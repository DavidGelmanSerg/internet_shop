package ru.gelman.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.api_gateway.dto.CreateProductRq;
import ru.gelman.api_gateway.dto.ProductDto;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;

import java.net.URI;
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
    public ProductDto createProduct(@RequestPart("productInfo") CreateProductRq productRq, @RequestPart("images") List<MultipartFile> images) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("productInfo", productRq);
        images.forEach(img -> body.add(img.getName(), img));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(
                "",
                requestEntity,
                ProductDto.class).getBody();
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProductInfo(@RequestBody CreateProductRq productRq, @PathVariable Long id) {
        RequestEntity<CreateProductRq> request = new RequestEntity<>(HttpMethod.PUT, URI.create(""));
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                "",
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<ProductDto>() {
                });
        return response.getBody();

    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {

    }

    @GetMapping("/products")
    public List<ProductDto> getProductList() {
        return null;
    }
}
