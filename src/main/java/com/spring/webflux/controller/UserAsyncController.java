package com.spring.webflux.controller;

import com.spring.webflux.service.UserAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/async-users")
public class UserAsyncController {

    @Autowired
    private UserAsyncService userAsyncService;

    @GetMapping
    public Mono<ResponseEntity<Map>> getAsyncUsers() {
        return Mono.zip(
                userAsyncService.callExternalServiceOne(),
                userAsyncService.callExternalServiceTwo(),
                userAsyncService.callExternalServiceThree()
        ).map(results -> {
            Map<String, Object> aggregatedData = Map.of(
                    "serviceOneData", results.getT1(),
                    "serviceTwoData", results.getT2(),
                    "serviceThreeData", results.getT3()
            );
            return ResponseEntity.ok(aggregatedData);
        });
    }
}
