package com.adhaar.webcrawler.domainkeysets.repository;

import com.adhaar.webcrawler.domainkeysets.entity.DomainKeySet;
import com.adhaar.webcrawler.products.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DomainKeySetRepository extends MongoRepository<DomainKeySet, String> {

}
