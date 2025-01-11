package com.adhaar.webcrawler.service;

import com.adhaar.webcrawler.products.entity.Product;
import com.adhaar.webcrawler.products.repository.ProductRepository;
import com.adhaar.webcrawler.websites.entity.Website;
import com.adhaar.webcrawler.websites.repository.WebsiteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class WebCrawlerService {

    private final WebsiteRepository websiteRepository;
    private final ProductRepository productRepository;
    @Autowired
    public WebCrawlerService(WebsiteRepository websiteRepository, ProductRepository productRepository) {
        this.websiteRepository = websiteRepository;
        this.productRepository = productRepository;
    }

    private static Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    private static final Queue<String> urlQueue = new ConcurrentLinkedQueue<>();
    private static final int MAX_THREADS = 10;



    /**
     * Method to crawl product URLs from the given domains.
     *
     * @param domains List of domains to crawl.
     * @return A list of discovered product URLs.
     */
    public List<Product> crawlDomains(List<String> domains) throws InterruptedException, IOException {
        return crawl(domains);
    }

    public List<Product> crawl(List<String> seedUrls) throws InterruptedException, IOException {
        visitedUrls = Collections.synchronizedSet(new HashSet<>());
        List<Product> products = new ArrayList<>();

        for (String seedUrl : seedUrls) {
            urlQueue.add(seedUrl);
        }

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll();
            String origin = ECommerceLinkExtractor.cleanUrl(url);
            Optional<Website> existingWebsite = websiteRepository.findByUrl(origin);
            if (!existingWebsite.isPresent()) {
                // Add
                addWebsite(origin);
            }
            if (url == null || visitedUrls.contains(url)) continue;

            executor.submit(() -> {
                WebDriver driver = null;
                try {
                    driver = new FirefoxDriver();
                    driver.get(url);
                    visitedUrls.add(url);
                    /*Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                            .timeout(6000)
                            .get();*/
                    Document doc = Jsoup.parse(driver.getPageSource());
                    List<String> linkFilter = ECommerceLinkExtractor.getUrlFilters(url);
                    String productLinkFilter = linkFilter.get(0);
                    Elements productLinks;
                    if(ECommerceLinkExtractor.eCommerceMappingsHas(url)){
                        productLinks = doc.select(productLinkFilter);
                    } else {
                        // Extract product links with improved selector
                        productLinks = doc.select("a[href*='/itm/']");
                        productLinks.addAll(doc.select("a[href*='/product/']"));
                        productLinks.addAll(doc.select("a[href*='/prd/']"));
                        productLinks.addAll(doc.select("a[href*='/dp/']"));
                    }
                    // Print each product URL
                    for (Element link : productLinks) {
                        String productUrl = link.attr("href"); // Get absolute URL
                        if (!visitedUrls.contains(productUrl)) {
                            // Get current time in milliseconds
                            long currentMillis = System.currentTimeMillis();

                            Optional<Product> existingProduct = productRepository.findByUrl(origin);
                            Product product;
                            if (!existingProduct.isPresent()) {
                                // Add
                                product = addProduct(currentMillis, productUrl, origin);
                            } else {
                                // Update
                                product = updateProduct(existingProduct.stream().findFirst().get());
                            }

                            System.out.println("Found Product URL: " + productUrl);
                            products.add(product);
                        }
                    }

                    String nextPageLinkFilter = linkFilter.get(1);
                    // Extract pagination links (handles infinite scrolling)
                    Elements nextPageLinks = doc.select(nextPageLinkFilter);
                    for (Element nextPage : nextPageLinks) {
                        String nextPageUrl = nextPage.attr("href");
                        if (!visitedUrls.contains(nextPageUrl)) {
                            urlQueue.add(nextPageUrl);
                        }
                    }
                    driver.quit();

                } catch (Exception e) {
                    System.err.println("Error processing URL: " + url + " - " + e.getMessage());
                } finally {
                    try {
                        if (driver != null) {
                            driver.quit();  // Ensure WebDriver stops properly
                        }
                    } catch (Exception e) {
                        e.printStackTrace();  // Handle any errors during driver quit
                    }
                }
            });
        }
        executor.awaitTermination(50000000, TimeUnit.MICROSECONDS);
        return products;
    }

    private Product updateProduct(Product product) {
        // Update the product to the database
        productRepository.save(product);
        return product;
    }

    private Product addProduct(long crawlTimeString, String url, String baseUrl) {
        Product product = createProduct(crawlTimeString, url, baseUrl);
        // Save the product to the database
        productRepository.save(product);
        return product;
    }

    private Website updateWebsite(Website website) {
        // Update the website to the database
        websiteRepository.save(website);
        return website;
    }

    private Website addWebsite(String url) {
        Website website = createWebsite(url);
        // Save the website to the database
        websiteRepository.save(website);
        return website;
    }

    private Website createWebsite(String url) {
        // Creating Website instance
        Website website = new Website();
        website.setUrl(url);
        return website;
    }

    private static Product createProduct(long crawlTimeString, String url, String baseUrl) {
        Date crawlTime = new Date(crawlTimeString);

        // Creating Product instance
        Product product = new Product();
        product.setUrl(url);
        product.setCrawlTime(crawlTime);
        product.setBaseUrl(baseUrl); // Setting the base URL
        return product;
    }
}