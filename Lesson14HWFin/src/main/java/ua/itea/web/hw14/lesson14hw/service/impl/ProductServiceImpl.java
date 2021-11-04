package ua.itea.web.hw14.lesson14hw.service.impl;

import org.springframework.stereotype.Service;
import ua.itea.web.hw14.lesson14hw.component.ProductMapper;
import ua.itea.web.hw14.lesson14hw.dto.ProductDto;
import ua.itea.web.hw14.lesson14hw.model.CategoryEntity;
import ua.itea.web.hw14.lesson14hw.model.ProductEntity;
import ua.itea.web.hw14.lesson14hw.repository.CategoryRepository;
import ua.itea.web.hw14.lesson14hw.repository.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ua.itea.web.hw14.lesson14hw.service.ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto getProductById(int id) {
        return productMapper.entityToDto(productRepository.getById(id));
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryName) {
        List<ProductEntity> allProducts;
        if (categoryName == null || categoryName.trim().isEmpty()) {
            allProducts = productRepository.findAll();
        } else {
            CategoryEntity categoryEntity = categoryRepository.findByName(categoryName);
            allProducts = productRepository.findByCategories(categoryEntity);
        }

        if (allProducts != null) {
            return allProducts.stream().map(productMapper::entityToDto).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
