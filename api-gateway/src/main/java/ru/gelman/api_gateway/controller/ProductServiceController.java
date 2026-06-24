package ru.gelman.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.api_gateway.dto.CreateProductRq;
import ru.gelman.api_gateway.dto.ProductDto;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;

import java.util.List;

public class ProductServiceController {
    private final AuthServiceClient authServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductServiceController(AuthServiceClient authServiceClient, OrderServiceClient orderServiceClient, RestTemplate restTemplate) {
        this.authServiceClient = authServiceClient;
        this.orderServiceClient = orderServiceClient;
        this.restTemplate = restTemplate;
    }

    @PostMapping(value = "/products", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> createProduct(@RequestPart("productInfo") CreateProductRq productRq, @RequestPart("images") List<MultipartFile> images, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.createProduct(productRq, images);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProductInfo(@RequestBody CreateProductRq productRq, @PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.updateProductInfo(id, productRq);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.deleteProduct(id);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProductList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.getProductList();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();

    }
}
