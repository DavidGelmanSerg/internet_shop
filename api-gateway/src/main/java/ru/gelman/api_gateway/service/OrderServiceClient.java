package ru.gelman.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.api_gateway.dto.CreateOrderRq;
import ru.gelman.api_gateway.dto.CreateProductRq;
import ru.gelman.api_gateway.dto.OrderDto;
import ru.gelman.api_gateway.dto.ProductDto;

import java.util.List;
import java.util.Map;

public class OrderServiceClient {
    private final RestTemplate restTemplate;
    private final RestClient client;
    @Value("app.services.orders.url")
    private String orderServiceUrl;

    @Autowired
    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.client = RestClient.create();
    }

    public ProductDto createProduct(CreateProductRq rq, List<MultipartFile> productImages) {
        return null;
    }

    public ResponseEntity<OrderDto> createOrder(CreateOrderRq rq) {
        return restTemplate.postForEntity(orderServiceUrl + "/orders", rq, OrderDto.class);
    }

    public ResponseEntity<List<OrderDto>> getOrderListForClient(Long id) {
        return restTemplate.exchange(String.format("%s/orders/%d", orderServiceUrl, id), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    public ResponseEntity<OrderDto> updateOrderStatus(Long id, String status) {
        return restTemplate.exchange(
                String.format("%s/orders/%d/status", orderServiceUrl, id),
                HttpMethod.PUT,
                null,
                OrderDto.class,
                Map.of("status", status)
        );
    }

    public ResponseEntity<Void> addProducts(Long id, List<Long> productIds) {
        return client
                .put()
                .uri(String.format("%s/orders/%d/products/add", orderServiceUrl, id))
                .body(productIds).retrieve().toBodilessEntity();
    }

    public ResponseEntity<Void> removeProducts(Long id, List<Long> productIds) {
        return client
                .put()
                .uri(String.format("%s/orders/%d/products/remove", orderServiceUrl, id))
                .body(productIds).retrieve().toBodilessEntity();
    }
}
