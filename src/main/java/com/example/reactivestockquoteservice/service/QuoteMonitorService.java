package com.example.reactivestockquoteservice.service;

import com.example.reactivestockquoteservice.client.StockQuoteClient;
import com.example.reactivestockquoteservice.domain.Quote;
import com.example.reactivestockquoteservice.repository.QuoteRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class QuoteMonitorService implements ApplicationListener<ContextRefreshedEvent> {
    private final StockQuoteClient stockQuoteClient;
    private final QuoteRepository quoteRepository;

    public QuoteMonitorService(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
        this.stockQuoteClient = stockQuoteClient;
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        stockQuoteClient.getQuoteStream().log("Quote monitor service").subscribe(quote -> {
            Mono<Quote> savedQuote = quoteRepository.insert(quote);
            savedQuote.subscribe(result -> {
                log.info("Saved quote id {}", result.getId());
            });
        });
    }
}
