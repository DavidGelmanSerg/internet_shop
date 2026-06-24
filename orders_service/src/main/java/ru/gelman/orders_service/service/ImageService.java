package ru.gelman.orders_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gelman.orders_service.entity.Image;
import ru.gelman.orders_service.entity.Product;
import ru.gelman.orders_service.exception.NotFoundException;
import ru.gelman.orders_service.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository repository) {
        this.imageRepository = repository;
    }

    public Image getImageById(Long id) {
        log.info("searching image by id {}", id);
        return imageRepository.findById(id).orElseThrow(() -> NotFoundException.imageNotFound(id));
    }

    public List<Image> saveImagesForProduct(List<Image> images, Product product) {
        if (images.isEmpty()) {
            return new ArrayList<>();
        }
        images.get(0).setPreview(true);
        List<Image> result = new ArrayList<>();
        for (Image image : images) {
            image.setName(UUID.randomUUID().toString());
            image.setProduct(product);
            log.info("saving image: name={}, contentType={}, size={}, preview={} for product: {}", image.getName(), image.getContentType(), image.getSize(), image.isPreview(), product.getId());
            result.add(imageRepository.save(image));
        }
        log.info("saved {} images for product: {}", result.size(), product.getId());
        log.info("saved images: {}", result);
        return result;
    }
}
