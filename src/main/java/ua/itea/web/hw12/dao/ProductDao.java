package ua.itea.web.hw12.dao;

import ua.itea.web.hw12.model.ProductDto;

import java.util.List;

public interface ProductDao {
    List<ProductDto> getProducts();

    ProductDto getProductById(String id);

    List<ProductDto> getProductsByCategory(String category);
}
