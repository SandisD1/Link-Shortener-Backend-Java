package io.linkshortener.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AggregatedStats {
    private int totalShortenedUrlCount;
    private int shortenedUrlsConsumedCount;
}
