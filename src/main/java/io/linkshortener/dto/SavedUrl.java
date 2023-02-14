package io.linkshortener.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Entity
@Table(name = "saved_url")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SavedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String fullUrl;

    @Column(name = "short_url")
    private String url;

    private LocalDate expires;

    private Integer timesConsumed;


    public SavedUrl(String url) {
        this.fullUrl = url;
    }

    public SavedUrl() {
    }

    public void setExpiration(Expiration expiration) {
        LocalDate now = LocalDate.now();
        LocalDate whenExpires;
        if (expiration.getUnit().equals("days")) {
            whenExpires = now.plusDays(expiration.getAmount());
        } else {
            int daysToExpire = expiration.getAmount() * 7;
            whenExpires = now.plusDays(daysToExpire);
        }
        this.expires = whenExpires;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getExpires() {
        return expires;
    }
}
