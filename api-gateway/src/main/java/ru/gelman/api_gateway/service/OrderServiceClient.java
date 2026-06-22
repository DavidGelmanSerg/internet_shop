package ru.gelman.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.api_gateway.dto.CreateProductRq;
import ru.gelman.api_gateway.dto.ProductDto;

import java.util.List;

public class OrderServiceClient {
    private final RestTemplate restTemplate;
    @Value("app.services.orders.url")
    private String orderServiceUrl;

    @Autowired
    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDto createProduct(CreateProductRq rq, List<MultipartFile> productImages) {
        return null;
    }
}
