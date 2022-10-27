package com.example.pingpong_webflux_using_high_letency_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockHound;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void ping() {
        // Enable operator stack recorder that captures
        // a declaration stack whenever an operator is instantiated
        BlockHound.install();

        // Example URL: http://localhost:8000/ping
        webTestClient.get().uri("/ping")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body).isEqualTo("pongfromAPI"));
    }
}