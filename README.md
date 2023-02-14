# Link Shortener API

- Run the `LinkShortenerApplication.java` class.
- It is located in the `src/main/java/io/linkshortener` package.

# Endpoints

- POST request to `http://localhost:8080/api/shortened-urls/shorten` to add and shorten a URL.
- GET request to `http://localhost:8080/api/statistics/shortened-urls` to receive stats on number of URLs saved and the times shortened URLs have been used to redirect.
- GET request to `http://localhost:8080/testing-api/reset` to clear entries from database. Requires authorisation with credentials from `src/main/resources/application.properties`.

# Redirects

- Use the shortened links provided though the front end application to be redirected to the initially submitted URL.