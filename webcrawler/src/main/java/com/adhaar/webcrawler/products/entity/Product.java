package com.adhaar.webcrawler.products.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "products")
@Getter
@Setter
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    private String url;
    private Date crawlTime;
    private String baseUrl; // Base URL of the webpage

}
