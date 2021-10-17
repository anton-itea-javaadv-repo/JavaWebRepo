package ua.itea.hw09.dao;

import ua.itea.hw09.model.ProductDto;

import java.util.List;

public interface ProductDao {
    List<ProductDto> getProducts();

    ProductDto getProductById(String id);

    List<ProductDto> getProductsByCategory(String category);
}
