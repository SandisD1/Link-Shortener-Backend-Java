package io.linkshortener.controllers;

import io.linkshortener.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testing-api")
@RequiredArgsConstructor
class TestingApiController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/reset")
    public void reset() {
        this.shortUrlService.reset();
    }

}
