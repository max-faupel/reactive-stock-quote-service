package com.example.reactivestockquoteservice.repository;

import com.example.reactivestockquoteservice.domain.Quote;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuoteRepository extends ReactiveMongoRepository<Quote, String> {
    
}
