package com.adhaar.webcrawler.websites.controller;

import com.adhaar.webcrawler.websites.entity.Website;
import com.adhaar.webcrawler.websites.service.WebsiteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

    @GetMapping("/websites")
    public ResponseEntity<List<Website>> getAllWebsite(){
        return ResponseEntity.ok().body(websiteService.getAllWebsites());
    }

    @GetMapping("/websites/{id}")
    public ResponseEntity<Website> getWebsiteById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok().body(websiteService.getWebsiteById(id));
    }

    @PostMapping("/websites/create")
    public ResponseEntity<Website> createWebsite(@RequestBody Website website){
        return ResponseEntity.ok().body(this.websiteService.createWebsite(website));
    }

    @PutMapping("/websites/{id}")
    public ResponseEntity<Website> updateWebsite(@PathVariable String id, @RequestBody Website website) throws Exception {
        website.setId(id);
        return ResponseEntity.ok().body(this.websiteService.updateWebsite(website));
    }

    @DeleteMapping("/websites/{id}")
    public HttpStatus deleteWebsite(@PathVariable String id) throws Exception {
        this.websiteService.deleteWebsite(id);
        return HttpStatus.OK;
    }
}