package io.linkshortener.service;


import io.linkshortener.dto.AggregatedStats;
import io.linkshortener.dto.ReceivedUrl;
import io.linkshortener.dto.ShortUrl;

public interface ShortUrlService {

    ShortUrl processUrl(ReceivedUrl receivedUrl);

    String retrieveFullUrlFromShortUrl(String shortUrl);

    AggregatedStats countShortenedUrls();

    void reset();

}
