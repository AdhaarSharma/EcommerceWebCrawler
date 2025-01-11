package com.adhaar.webcrawler.websites.repository;

import com.adhaar.webcrawler.websites.entity.Website;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WebsiteRepository extends MongoRepository<Website, String>{
    Optional<Website> findByUrl(String url);
}