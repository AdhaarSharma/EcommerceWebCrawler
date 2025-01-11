package com.adhaar.webcrawler.websites.entity;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "websites")
@Getter
@Setter
public class Website {

    @Id
    private String id;

    @Indexed(unique=true)
    private String url;

}