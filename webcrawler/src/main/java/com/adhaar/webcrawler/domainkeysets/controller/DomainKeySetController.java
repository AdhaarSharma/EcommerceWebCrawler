package com.adhaar.webcrawler.domainkeysets.controller;

import com.adhaar.webcrawler.domainkeysets.entity.DomainKeySet;
import com.adhaar.webcrawler.domainkeysets.service.DomainKeySetService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/domainkeysets")
public class DomainKeySetController {

    @Autowired
    private DomainKeySetService domainKeySetService;

    @GetMapping
    public ResponseEntity<List<DomainKeySet>> getAllDomainKeySets() {
        return ResponseEntity.ok().body(domainKeySetService.getAllDomainKeySets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomainKeySet> getDomainKeySetById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok().body(domainKeySetService.getDomainKeySetById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<DomainKeySet> createDomainKeySet(@RequestBody DomainKeySet domainKeySet) {
        return ResponseEntity.ok().body(domainKeySetService.createDomainKeySet(domainKeySet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomainKeySet> updateDomainKeySet(@PathVariable String id, @RequestBody DomainKeySet domainKeySet) throws Exception {
        domainKeySet.setId(id);
        return ResponseEntity.ok().body(domainKeySetService.updateDomainKeySet(domainKeySet));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteDomainKeySet(@PathVariable String id) throws Exception {
        domainKeySetService.deleteDomainKeySet(id);
        return HttpStatus.OK;
    }
}
