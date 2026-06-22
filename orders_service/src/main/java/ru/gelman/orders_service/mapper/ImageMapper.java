package ru.gelman.orders_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;
import ru.gelman.orders_service.dto.ImageDto;
import ru.gelman.orders_service.entity.Image;

import java.io.IOException;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImageMapper {
    ImageDto toImageDto(Image image);

    List<ImageDto> toImageDtoList(List<Image> images);

    Image toImageEntity(MultipartFile file) throws IOException;

    List<Image> toImageListEntity(List<MultipartFile> files) throws IOException;
}
