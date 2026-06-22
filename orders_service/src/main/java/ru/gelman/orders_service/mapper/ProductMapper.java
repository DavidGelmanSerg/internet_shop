package ru.gelman.orders_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.gelman.orders_service.dto.CreateProductRq;
import ru.gelman.orders_service.dto.ImageDto;
import ru.gelman.orders_service.dto.ProductDto;
import ru.gelman.orders_service.entity.Product;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);

    ProductDto toProductDto(Product product, List<ImageDto> imageInfos);

    ProductDto toProductDto(CreateProductRq rq);

    ProductDto toProductDto(CreateProductRq rq, Long id);

}
