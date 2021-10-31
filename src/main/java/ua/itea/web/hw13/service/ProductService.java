package ua.itea.web.hw13.service;

import org.springframework.stereotype.Service;
import ua.itea.web.hw13.component.ProductEntityManager;
import ua.itea.web.hw13.component.ProductMapper;
import ua.itea.web.hw13.dto.ProductDto;
import ua.itea.web.hw13.model.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductEntityManager productEm;
    private final ProductMapper productMapper;

    public ProductService(ProductEntityManager productEm, ProductMapper productMapper) {
        this.productEm = productEm;
        this.productMapper = productMapper;
    }

    public ProductDto getProductById(int id) {
        return productMapper.entityToDto(productEm.getProductById(id));
    }

    public List<ProductDto> getProductsByCategory(String categoryName) {
        List<ProductEntity> allProducts;
        if (categoryName == null) {
            allProducts = productEm.getProducts();
        } else {
            allProducts = productEm.getProductsByCategory(categoryName);
        }

        if (allProducts != null) {
            return allProducts.stream().map(productMapper::entityToDto).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
