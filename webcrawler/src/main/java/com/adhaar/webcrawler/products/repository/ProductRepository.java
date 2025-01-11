package com.adhaar.webcrawler.products.repository;

import com.adhaar.webcrawler.products.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByUrl(String url);
}
