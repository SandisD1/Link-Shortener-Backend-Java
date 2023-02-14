package io.linkshortener.repository;


import io.linkshortener.dto.SavedUrl;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SavedUrlRepository extends JpaRepository<SavedUrl, Integer> {

    @Query("SELECT s.fullUrl FROM SavedUrl s WHERE s.url = :shortUrl")
    Optional<String> getFullUrlFromShort(String shortUrl);

    @Query("SELECT COUNT(s.url) FROM SavedUrl s WHERE s.url = :shortUrl")
    Integer checkDuplicateShortUrl(String shortUrl);

    @Transactional
    @Modifying
    @Query("UPDATE SavedUrl s SET s.timesConsumed = s.timesConsumed+1 WHERE s.url = :shortUrl")
    void updateConsumedCount(String shortUrl);

    @Query("SELECT SUM(s.timesConsumed) FROM SavedUrl s")
    Integer getTotalConsumed();

    @Transactional
    @Modifying
    @Query("DELETE FROM SavedUrl s WHERE s.expires<=CURRENT_DATE")
    void deleteExpired();
}
