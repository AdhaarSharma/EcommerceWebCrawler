package com.adhaar.webcrawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class ECommerceLinkExtractor {

    private static final Map<String, String[]> eCommerceMappings = new HashMap<>();

    static {
        eCommerceMappings.put("ebay.com", new String[]{"a[href*='/itm/']", "a.pagination__next, a.next"});
        eCommerceMappings.put("meesho.com", new String[]{"a[href*='/product/']", "a.pagination-next, a.next-button"});
        eCommerceMappings.put("flipkart.com", new String[]{"a[href*='/product/']", "a.next-page, a.pagination-next"});
        eCommerceMappings.put("snapdeal.com", new String[]{"a[href*='product/']", "a.pagination-next, a.pagination__next[href*='page=']"});
        eCommerceMappings.put("indiamart.com", new String[]{
                "a[href*='/proddetail/']", // Product Links Selector
                "a[rel='next'], a[href*='page=']" // Next Page Links Selector
        });
        eCommerceMappings.put("firstcry.com", new String[]{"a[href*='/product/']", "a.next-page, a.pagination__next"});
        eCommerceMappings.put("nykaa.com", new String[]{"a[href*='/prd/']", "a.pagination__next, a.next-page"});
        eCommerceMappings.put("relianceretail.com", new String[]{"a[href*='product/']", "a.pagination-next, a[href*='&page=']"});
        eCommerceMappings.put("tatacliq.com", new String[]{"a[href*='/product/']", "a.pagination-next, a[href*='page=']"});
        eCommerceMappings.put("amazon.com", new String[]{"a[href*='/dp/']", "ul.a-pagination a[aria-label='Next']"});
        eCommerceMappings.put("myntra.com", new String[]{"a[href*='/buy']", "a.pagination__next, a.next"});
        eCommerceMappings.put("ajio.com", new String[]{"a[href*='/product/']", "a.pagination__next, a.next"});
        eCommerceMappings.put("purplefashion.in", new String[]{"a[href*='product/']", "a.next-page, a.pagination-next"});
        eCommerceMappings.put("lenskart.com", new String[]{"a[href*='product/']", "a.next-page, a.pagination__next"});
        eCommerceMappings.put("boat-lifestyle.com", new String[]{"a[href*='product/']", "a.pagination-next"});
        eCommerceMappings.put("shoppersstop.com", new String[]{"a[href*='/prd/']", "a.pagination__next, a.next"});
        eCommerceMappings.put("myntravalues.com", new String[]{"a[href*='/prd/']", "a.pagination__next"});

        // Added more e-commerce sites
        eCommerceMappings.put("myntraclayco.in", new String[]{"a[href*='/prd/']", "a.pagination__next"});
        eCommerceMappings.put("homeshop18.com", new String[]{"a[href*='/product/']", "a.next-pagination"});
        eCommerceMappings.put("craftsvilla.com", new String[]{"a[href*='/product/']", "a.pagination-next, a[href*='page=']"});
        eCommerceMappings.put("koovs.com", new String[]{"a[href*='/product/']", "a.pagination__next"});
        eCommerceMappings.put("kazo.com", new String[]{"a[href*='/product/']", "a.pagination__next"});
        eCommerceMappings.put("hrxbrand.com", new String[]{"a[href*='product/']", "a.next-page"});
        eCommerceMappings.put("trendybharat.com", new String[]{"a[href*='/product/']", "a.next-pagination"});
        eCommerceMappings.put("voonik.com", new String[]{"a[href*='/product/']", "a.next-pagination"});
        eCommerceMappings.put("juvalia.com", new String[]{"a[href*='product/']", "a.pagination__next"});

        // Fallback general mapping for URLs that don't match specific e-commerce sites
        eCommerceMappings.put("default", new String[]{"a[href]", "a.pagination__next, a.next"});
    }

    public static List<String> getUrlFilters(String url) {
        return extractLinksFromBaseUrl(url);
    }

    public static boolean eCommerceMappingsHas(String url) {
        return eCommerceMappings.containsKey(cleanUrl(url));
    }

    public static List<String> extractLinksFromBaseUrl(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            String baseUrl = doc.baseUri();  // Extract the base URL
            System.out.println("Base URL: " + baseUrl);
            baseUrl = cleanUrl(baseUrl);

            String[] mappings;
            if (eCommerceMappings.containsKey(baseUrl)) {
                mappings = eCommerceMappings.get(baseUrl);
            } else {
                mappings = eCommerceMappings.get("default");
            }
            List<String> mapping = new ArrayList<>();
            Collections.addAll(mapping, mappings);
            return mapping;

        } catch (IOException e) {
            System.err.println("Error processing URL: " + url + " - " + e.getMessage());
        }
        return null;
    }

    public static String cleanUrl(String url) {
        String cleanedUrl = url
                .replaceFirst("https?://(www\\.)?", "")  // Remove "https://www" or "http://www"
                .replaceFirst("^www\\.", "")              // Remove "www"
                .replaceAll("/.*", "");                    // Remove trailing URL path
        return cleanedUrl;
    }
}
