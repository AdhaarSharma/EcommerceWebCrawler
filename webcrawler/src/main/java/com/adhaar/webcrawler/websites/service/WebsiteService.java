package com.adhaar.webcrawler.websites.service;


import com.adhaar.webcrawler.websites.entity.Website;
import com.adhaar.webcrawler.websites.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WebsiteService {


    @Autowired
    private WebsiteRepository websiteRepository;


    public Website createWebsite(Website website) {
        return websiteRepository.save(website);
    }

    public List<Website> getAllWebsites() {
        return this.websiteRepository.findAll();
    }


    public Website updateWebsite(Website website) throws Exception {
        Optional<Website> websiteDb = this.websiteRepository.findById(website.getId());

        if(websiteDb.isPresent()) {
            Website websiteUpdate = websiteDb.get();
            websiteUpdate.setId(website.getId());
            websiteUpdate.setUrl(website.getUrl());
            websiteRepository.save(websiteUpdate);
            return websiteUpdate;
        }else {
            throw new Exception("Record not found with id : " + website.getId());
        }
    }

    public Website getWebsiteById(String websiteId) throws Exception {

        Optional<Website> websiteDb = this.websiteRepository.findById(websiteId);

        if(websiteDb.isPresent()) {
            return websiteDb.get();
        }else {
            throw new Exception("Record not found with id : " + websiteId);
        }
    }

    public void deleteWebsite(String websiteId) throws Exception {
        Optional<Website> websiteDb = this.websiteRepository.findById(websiteId);

        if(websiteDb.isPresent()) {
            this.websiteRepository.delete(websiteDb.get());
        }else {
            throw new Exception("Record not found with id : " + websiteId);
        }

    }

}