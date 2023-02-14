package io.linkshortener.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ShortUrl {
    private String url;
    private LocalDate expires;

    public ShortUrl(String url, LocalDate expires) {
        this.url = url;
        this.expires = expires;
    }
}
