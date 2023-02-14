package io.linkshortener.service;

import io.linkshortener.dto.AggregatedStats;
import io.linkshortener.dto.ReceivedUrl;
import io.linkshortener.dto.SavedUrl;
import io.linkshortener.dto.ShortUrl;
import io.linkshortener.repository.SavedUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "link-shortener", name = "appmode", havingValue = "in-database")
public class ShortUrlsService implements ShortUrlService {
    private final SavedUrlRepository savedUrlRepository;
    @Value("${link-shortener.domain}")
    private String baseUrl;

    @Override
    public ShortUrl processUrl(ReceivedUrl receivedUrl) {

        String receivedLink = receivedUrl.getUrl();
        if (!isValidUrl(receivedLink)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid URL");
        }
        String shortUrl = uniqueShortUrl();
        SavedUrl saved = fromReceivedUrl(receivedUrl);
        saved.setUrl(shortUrl);
        SavedUrl savedUrl = this.savedUrlRepository.save(saved);

        String redirectMapping = "api/redirects/";
        String shortUrlWithRedirect = baseUrl + redirectMapping + shortUrl;

        return new ShortUrl(shortUrlWithRedirect, savedUrl.getExpires());
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();

        } catch (MalformedURLException | URISyntaxException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid URL");
        }
        return true;
    }

    private String createShortUrl() {

        StringBuilder shortLink = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(57) + 65;
            if ((number > 90) && (number < 97)) {
                number += 7;
            }
            char letter = (char) number;
            shortLink.append(letter);
        }
        return shortLink.toString();
    }

    private String uniqueShortUrl() {
        String tryShortUrl;
        for (int i = 0; i < 10; i++) {
            tryShortUrl = createShortUrl();
            if (this.savedUrlRepository.checkDuplicateShortUrl(tryShortUrl) == 0) {
                return tryShortUrl;
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "short URL generation failed, please try again");
    }

    private SavedUrl fromReceivedUrl(ReceivedUrl receivedUrl) {
        SavedUrl saved = new SavedUrl(receivedUrl.getUrl());
        saved.setTimesConsumed(0);
        if (receivedUrl.getExpiration() != null) {
            saved.setExpiration(receivedUrl.getExpiration());
        }
        return saved;
    }

    @Override
    public String retrieveFullUrlFromShortUrl(String shortUrl) {

        System.out.println(shortUrl);

        String fullUrl = savedUrlRepository.getFullUrlFromShort(shortUrl).orElse(null);
        if (fullUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        savedUrlRepository.updateConsumedCount(shortUrl);
        return fullUrl;
    }

    @Override
    public AggregatedStats countShortenedUrls() {

        int totalCount = (int) this.savedUrlRepository.count();
        int totalConsumed = 0;
        if (totalCount > 0) {
            totalConsumed = this.savedUrlRepository.getTotalConsumed();
        }
        return new AggregatedStats(totalCount, totalConsumed);
    }

    @Override
    public void reset() {
        this.savedUrlRepository.deleteAll();
    }

    @Scheduled(cron = "0 1 0 * * *")
    private void clearExpired() {
        this.savedUrlRepository.deleteExpired();
        System.out.println("cleared");
    }
}
