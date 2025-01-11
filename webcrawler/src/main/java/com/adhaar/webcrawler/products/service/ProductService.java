package com.adhaar.webcrawler.products.service;

import com.adhaar.webcrawler.products.entity.Product;
import com.adhaar.webcrawler.products.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) throws Exception {
        Optional<Product> productDb = productRepository.findById(product.getId());

        if (productDb.isPresent()) {
            Product productUpdate = productDb.get();
            productUpdate.setUrl(product.getUrl());
            productUpdate.setCrawlTime(product.getCrawlTime());
            productUpdate.setBaseUrl(product.getBaseUrl());
            productRepository.save(productUpdate);
            return productUpdate;
        } else {
            throw new Exception("Record not found with id : " + product.getId());
        }
    }

    public Product getProductById(String productId) throws Exception {
        Optional<Product> productDb = productRepository.findById(productId);

        if (productDb.isPresent()) {
            return productDb.get();
        } else {
            throw new Exception("Record not found with id : " + productId);
        }
    }

    public void deleteProduct(String productId) throws Exception {
        Optional<Product> productDb = productRepository.findById(productId);

        if (productDb.isPresent()) {
            productRepository.delete(productDb.get());
        } else {
            throw new Exception("Record not found with id : " + productId);
        }
    }
}
