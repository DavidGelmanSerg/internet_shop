package ru.gelman.orders_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gelman.orders_service.dto.ProductDto;
import ru.gelman.orders_service.entity.Image;
import ru.gelman.orders_service.entity.Product;
import ru.gelman.orders_service.exception.NotFoundException;
import ru.gelman.orders_service.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final ImageService imageService;

    @Autowired
    public ProductService(ProductRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    public Product create(ProductDto productData, List<Image> images) {
        Product product = new Product();
        product.setName(productData.name());
        product.setDescription(productData.description());
        product.setAmount(productData.amount());
        product.setCost(productData.cost());

        log.info("saving new product: {}", product);
        Product created = repository.save(product);

        if (!images.isEmpty()) {
            log.info("saving images for product: {}", created);
            List<Image> imageList = imageService.saveImagesForProduct(images, created);
            created.setImages(imageList);
        }
        return repository.save(created);
    }

    public Product update(ProductDto productData) {
        Product product = repository.findById(productData.id())
                .orElseThrow(() -> NotFoundException.productNotFound(productData.id()));

        log.info("updating product {} with data {}", product, productData);
        product.setName(productData.name());
        product.setDescription(productData.description());
        product.setAmount(productData.amount());

        log.info("saving updated product {}", product);
        return repository.save(product);
    }

    public void delete(Long id) {
        log.info("searching product by id: {}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> NotFoundException.productNotFound(id));

        if (product.getOrders().isEmpty()) {
            log.info("deleting product {}", product);
            repository.delete(product);
        }
        throw new UnsupportedOperationException(String.format("product %s is ordered. deleting is not allowed", product.getId()));
    }

    public List<Product> getProducts() {
        List<Product> products = repository.findAll();
        log.info("found product: {}", products);
        return products;
    }
}
