package com.adhaar.webcrawler.controller;

import com.adhaar.webcrawler.products.entity.Product;
import com.adhaar.webcrawler.service.WebCrawlerService;
import com.adhaar.webcrawler.websites.entity.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class WebCrawlerController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    public WebCrawlerController(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    /**
     * Endpoint to start the web crawling process.
     *
     * @param domains List of e-commerce domains to crawl.
     * @return A list of discovered product URLs.
     */
    @PostMapping("/webcrawler/crawl")
    public ResponseEntity<List<Product>> crawl(@RequestBody List<String> domains) throws InterruptedException, IOException {
        return ResponseEntity.ok().body(webCrawlerService.crawlDomains(domains));
    }
}