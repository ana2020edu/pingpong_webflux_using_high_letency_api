package com.example.pingpong_webflux_using_high_letency_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class MyController {

    private final WebClient webClient;

    public MyController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("ping")
    public Mono<String> ping() {
        return webClient.get().uri("http://localhost:8000/ping")
                .retrieve()
                .bodyToMono(String.class).map(x -> x + "fromAPI");
    }
}
