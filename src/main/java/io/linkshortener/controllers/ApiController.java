package io.linkshortener.controllers;

import io.linkshortener.dto.AggregatedStats;
import io.linkshortener.dto.ReceivedUrl;
import io.linkshortener.dto.ShortUrl;
import io.linkshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
class ApiController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/shortened-urls/shorten")
    public ShortUrl receiveAndProcessUrl(@Valid @RequestBody ReceivedUrl receivedUrl) {
        return this.shortUrlService.processUrl(receivedUrl);
    }

    @GetMapping("/redirects/{shortUrl}")
    public RedirectView redirectWithShortUrl(@PathVariable String shortUrl) {
        String retrievedFullUrl = this.shortUrlService.retrieveFullUrlFromShortUrl(shortUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(retrievedFullUrl);
        return redirectView;
    }

    @GetMapping("/statistics/shortened-urls")
    public AggregatedStats shortenedUrls() {
        return this.shortUrlService.countShortenedUrls();
    }
}
