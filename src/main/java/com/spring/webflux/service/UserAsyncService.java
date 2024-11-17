package com.spring.webflux.service;

import com.spring.webflux.exception.CustomException;
import com.spring.webflux.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class UserAsyncService {

    private final WebClient webClient;

    public UserAsyncService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://run.mocky.io/v3").build();
    }

    public Mono<User> callExternalServiceOne() {
        return webClient.get()
                .uri("/f46ba09d-6206-4ecf-b1b8-621059b6520d")
                .retrieve()
                .bodyToMono(User.class)
                .delayElement(Duration.ofSeconds(2))
                .onErrorResume(e -> Mono.error(new CustomException("Error calling service one: " + e.getMessage())));
    }

    public Mono<User> callExternalServiceTwo() {
        return webClient.get()
                .uri("/7af39345-883e-4965-bb39-3010e446d47c")
                .retrieve()
                .bodyToMono(User.class)
                .delayElement(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.error(new CustomException("Error calling service two: " + e.getMessage())));
    }

    public Mono<User> callExternalServiceThree() {
        return webClient.get()
                .uri("/5dfc24ab-38a6-4e52-9332-ecc176b60948")
                .retrieve()
                .bodyToMono(User.class)
                .delayElement(Duration.ofSeconds(1))
                .onErrorResume(e -> Mono.error(new CustomException("Error calling service three: " + e.getMessage())));
    }
}
