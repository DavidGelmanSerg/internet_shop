package ru.gelman.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.api_gateway.dto.CreateProductRq;
import ru.gelman.api_gateway.dto.ProductDto;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;

import java.util.List;

public class ProductServiceController {
    private final AuthServiceClient authServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Autowired
    public ProductServiceController(AuthServiceClient authServiceClient, OrderServiceClient orderServiceClient) {
        this.authServiceClient = authServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @PostMapping(value = "/products", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProductDto createProduct(@RequestPart("productInfo") CreateProductRq productRq, @RequestPart("images") List<MultipartFile> images) {
        return null;
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProductInfo(@RequestBody CreateProductRq productRq, @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {

    }

    @GetMapping("/products")
    public List<ProductDto> getProductList() {
        return null;
    }
}
