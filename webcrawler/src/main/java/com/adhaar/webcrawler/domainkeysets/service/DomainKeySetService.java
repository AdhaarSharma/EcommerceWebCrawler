package com.adhaar.webcrawler.domainkeysets.service;

import com.adhaar.webcrawler.domainkeysets.entity.DomainKeySet;
import com.adhaar.webcrawler.domainkeysets.repository.DomainKeySetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DomainKeySetService {

    @Autowired
    private DomainKeySetRepository domainKeySetRepository;

    public DomainKeySet createDomainKeySet(DomainKeySet domainKeySet) {
        return domainKeySetRepository.save(domainKeySet);
    }

    public List<DomainKeySet> getAllDomainKeySets() {
        return domainKeySetRepository.findAll();
    }

    public DomainKeySet updateDomainKeySet(DomainKeySet domainKeySet) throws Exception {
        Optional<DomainKeySet> domainKeySetDb = domainKeySetRepository.findById(domainKeySet.getId());

        if (domainKeySetDb.isPresent()) {
            DomainKeySet domainKeySetUpdate = domainKeySetDb.get();
            domainKeySetUpdate.setId(domainKeySet.getId());
            domainKeySetRepository.save(domainKeySetUpdate);
            return domainKeySetUpdate;
        } else {
            throw new Exception("Record not found with id : " + domainKeySet.getId());
        }
    }

    public DomainKeySet getDomainKeySetById(String domainKeySetId) throws Exception {
        Optional<DomainKeySet> domainKeySetDb = domainKeySetRepository.findById(domainKeySetId);

        if (domainKeySetDb.isPresent()) {
            return domainKeySetDb.get();
        } else {
            throw new Exception("Record not found with id : " + domainKeySetId);
        }
    }

    public void deleteDomainKeySet(String domainKeySetId) throws Exception {
        Optional<DomainKeySet> domainKeySetDb = domainKeySetRepository.findById(domainKeySetId);

        if (domainKeySetDb.isPresent()) {
            domainKeySetRepository.delete(domainKeySetDb.get());
        } else {
            throw new Exception("Record not found with id : " + domainKeySetId);
        }
    }
}
