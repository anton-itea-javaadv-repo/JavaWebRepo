package ua.itea.web.hw14.lesson14hw.component;

import org.springframework.stereotype.Component;
import ua.itea.web.hw14.lesson14hw.dto.ProductDto;
import ua.itea.web.hw14.lesson14hw.model.ProductEntity;

@Component
public class ProductMapper {
    public ProductDto entityToDto(ProductEntity entity) {
        return new ProductDto()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice());
    }

    public ProductEntity dtoToEntity(ProductDto dto) {
        return new ProductEntity()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setPrice(dto.getPrice());
    }
}
