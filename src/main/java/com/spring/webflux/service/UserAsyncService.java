package com.spring.webflux.service;

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

    public Mono<Map> callExternalServiceOne() {
        return webClient.get()
                .uri("/f46ba09d-6206-4ecf-b1b8-621059b6520d")
                .retrieve()
                .bodyToMono(Map.class)
                .delayElement(Duration.ofSeconds(2))
                .onErrorResume(e -> Mono.just(Map.of("error", "Service one failed")));
    }

    public Mono<Map> callExternalServiceTwo() {
        return webClient.get()
                .uri("/7af39345-883e-4965-bb39-3010e446d47c")
                .retrieve()
                .bodyToMono(Map.class)
                .delayElement(Duration.ofSeconds(3))
                .onErrorResume(e -> Mono.just(Map.of("error", "Service two failed")));
    }

    public Mono<Map> callExternalServiceThree() {
        return webClient.get()
                .uri("/5dfc24ab-38a6-4e52-9332-ecc176b60948")
                .retrieve()
                .bodyToMono(Map.class)
                .delayElement(Duration.ofSeconds(1))
                .onErrorResume(e -> Mono.just(Map.of("error", "Service three failed")));
    }
}
