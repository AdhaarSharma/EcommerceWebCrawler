# EcommerceWebCrawler
Spring Boot Application for Web Crawler using MongoDB

Loom Video Link: https://www.loom.com/share/ae37c33c85ac438ca54c8e85d3268811?sid=ba9f15e1-d550-4450-8cca-99d94d9a1dfa

Requirements - 
1. Crawl domains and store product links
2. Get all product links
3. Get all the websites parsed
4. Handle infinite scrolling and pagination crawling 

Implementation -
1. Thought of the entities - 1) Product 2) Website 3) DomainKeyset
2. Created Controller, Service, Repository classes for these entities
3. Used MongoDB as database for being lightweight and schemaless. Ideal for large volume of read and write operation. Can be easily vertically scaled.
4. Came up with the endpoints and queries to be implemented
5. Used Spring JPA to implement these queries
6. Analyzed URL patterns in different ecommerce websites to filter product links in different website specifically
7. Also created a general URL pattern for the unforseen websites which gives a good result on average
8. Used JSOUP and Selenium for the url filtering and page loading
Kindly watch the video given above for indepth explaination and demonstration.

Future scope - 
1. We can use the webcrawler for getting the analytics on different product. For eg. we can see which product are trending for long time, which are the best sellers, which one go out of stock soon, or find some kind of relationship between products from different ecommerce website to understand market demand in a better manner
2. We can use headless browser drivers in Selenium and try to find a way to partially load the page in a such a way that we get all the links but nothing more (unnecessary media - images, audio, video etc)
3. We can increase the depth of the crawler furthermore
4. We can get already fetched product links for a particular website
5. We can make the webcrawler more polite by avoiding accidental DoS on target ecommerce websites


