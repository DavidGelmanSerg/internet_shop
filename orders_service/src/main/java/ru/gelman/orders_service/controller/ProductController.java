package ru.gelman.orders_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.orders_service.dto.CreateProductRq;
import ru.gelman.orders_service.dto.ProductDto;
import ru.gelman.orders_service.entity.Product;
import ru.gelman.orders_service.exception.ImageLoadException;
import ru.gelman.orders_service.mapper.ImageMapper;
import ru.gelman.orders_service.mapper.ProductMapper;
import ru.gelman.orders_service.service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ImageMapper imageMapper;

    @Autowired
    public ProductController(ProductService service, ProductMapper mapper, ImageMapper imageMapper) {
        this.productService = service;
        this.productMapper = mapper;
        this.imageMapper = imageMapper;
    }

    @PostMapping(value = "/products", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProductDto createProduct(@RequestPart("productInfo") CreateProductRq productRq, @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            log.info("creating a new product: {}", productRq);
            Product created = productService.create(productMapper.toProductDto(productRq), imageMapper.toImageListEntity(images));
            log.info("created product: {}", created);
            return productMapper.toProductDto(created, imageMapper.toImageDtoList(created.getImages()));
        } catch (IOException e) {
            throw new ImageLoadException("failed to load images for product:");
        }
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProductInfo(@RequestBody CreateProductRq productRq, @PathVariable Long id) {
        log.info("updating product with id: {}", id);
        Product updated = productService.update(productMapper.toProductDto(productRq, id));
        log.info("updated product: {}", updated);
        return productMapper.toProductDto(updated);
    }


    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        log.info("deleting product with id: {}", id);
        productService.delete(id);
    }

    @GetMapping("/products")
    public List<ProductDto> getProductList() {
        log.info("getting product list");
        return productService.getProducts()
                .stream()
                .map(product -> productMapper.toProductDto(product, imageMapper.toImageDtoList(product.getImages())))
                .toList();
    }
}
