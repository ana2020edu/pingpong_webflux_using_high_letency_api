package com.example.pingpong_webflux_using_high_letency_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.function.Function;

@Configuration
public class MyConfig {

    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("MyConnectionProvider")
                .maxConnections(2000) // Please put the appropriate value in the service
                .pendingAcquireMaxCount(2000) // Please put the appropriate value in the service
                .build();
        ReactorResourceFactory reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setUseGlobalResources(false);
        reactorResourceFactory.setConnectionProvider(connectionProvider);
        return reactorResourceFactory;
    }

    @Bean
    public WebClient webClient() {
        Function<HttpClient, HttpClient> mapper = client -> {
            // Please insert the code to customize here
            return client;
        };

        ReactorClientHttpConnector reactorClientHttpConnector
                = new ReactorClientHttpConnector(reactorResourceFactory(), mapper);

        return WebClient.builder().clientConnector(reactorClientHttpConnector).build();
    }
}
