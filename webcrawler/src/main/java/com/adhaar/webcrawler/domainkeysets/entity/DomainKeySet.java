package com.adhaar.webcrawler.domainkeysets.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
public class DomainKeySet {

    @Id
    private String id;

    @Indexed(unique=true)
    private String key;
}
