package com.course.service.myRestClient;

import com.course.exception.myApiException.MainExceptionForExternalApi;
import com.course.model.response.WeatherApiResponse;
import com.course.model.weatherApi.ApiError;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherRestClient {
    private final WebClient webClient;

    @Value("${api.path}")
    private String path;

    @Value("${api.key}")
    private String apiKey;

    public WeatherRestClient(@Qualifier("weatherApiClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @RateLimiter(name = "apiLimiter")
    public ResponseEntity<WeatherApiResponse> getCurrentWeather(String cityName) {
        ResponseEntity<WeatherApiResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("key", apiKey)
                        .queryParam("q", cityName)
                        .build())
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        apiResponse -> apiResponse.bodyToMono(ApiError.class)
                                .flatMap(apiError -> Mono.error(new MainExceptionForExternalApi(apiError.error().code())))
                )
                .toEntity(WeatherApiResponse.class)
                .block();

        return response;
    }
}
