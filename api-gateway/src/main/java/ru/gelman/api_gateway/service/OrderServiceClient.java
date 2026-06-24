package ru.gelman.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.api_gateway.dto.CreateOrderRq;
import ru.gelman.api_gateway.dto.CreateProductRq;
import ru.gelman.api_gateway.dto.OrderDto;
import ru.gelman.api_gateway.dto.ProductDto;

import java.util.List;
import java.util.Map;

@Component
public class OrderServiceClient {
    private final RestTemplate restTemplate;
    private final RestClient client;
    @Value("${app.services.orders.url}")
    private String orderServiceUrl;

    @Autowired
    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.client = RestClient.create();
    }

    public ResponseEntity<ProductDto> createProduct(CreateProductRq rq, List<MultipartFile> productImages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("productInfo", rq);
        productImages.forEach(img -> body.add(img.getName(), img));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(
                String.format("%s/products", orderServiceUrl),
                requestEntity,
                ProductDto.class);
    }

    public ResponseEntity<OrderDto> createOrder(CreateOrderRq rq) {
        return restTemplate.postForEntity(orderServiceUrl + "/orders", rq, OrderDto.class);
    }

    public ResponseEntity<List<OrderDto>> getOrderListForClient(Long id) {
        return restTemplate.exchange(
                String.format("%s/orders/%d", orderServiceUrl, id),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
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

    public ResponseEntity<ProductDto> updateProductInfo(Long id, CreateProductRq productRq) {
        return client
                .put()
                .uri(String.format("%s/products/%d", orderServiceUrl, id))
                .body(productRq)
                .retrieve()
                .toEntity(ProductDto.class);
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        return client
                .delete()
                .uri(String.format("%s/products/%d", orderServiceUrl, id))
                .retrieve()
                .toBodilessEntity();
    }

    public ResponseEntity<List<ProductDto>> getProductList() {
        return client
                .get()
                .uri(String.format("%s/products"), orderServiceUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });

    }

    public ResponseEntity<Resource> getImage(Long id) {
        return client
                .get()
                .uri(String.format("%s/images/%d"), orderServiceUrl, id)
                .retrieve()
                .toEntity(Resource.class);
    }
}
