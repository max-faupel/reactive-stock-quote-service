package com.example.reactivestockquoteservice.client;

import com.example.reactivestockquoteservice.domain.Quote;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Setter
@Component
@ConfigurationProperties("stockquote")
public class StockQuoteClient {
    private String host;
    private String port;
    private String path;

    public Flux<Quote> getQuoteStream() {
        String url = "http://" + host + ":" + port;
        log.debug("URL set is {}");
        return WebClient.builder().baseUrl(url).build().get().uri(path).accept(MediaType.APPLICATION_NDJSON).retrieve()
                .bodyToFlux(Quote.class);
    }
}
