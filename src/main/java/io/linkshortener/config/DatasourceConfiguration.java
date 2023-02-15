package io.linkshortener.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "link-shortener", name = "database", havingValue = "h2")
    public DataSource getDatabaseDataSourceH2() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:test")
                .username("name")
                .password("password")
                .build();
    }
}
