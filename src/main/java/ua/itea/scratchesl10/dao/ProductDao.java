package ua.itea.scratchesl10.dao;

import ua.itea.scratchesl10.model.ProductDto;

import java.util.List;

public interface ProductDao {
    List<ProductDto> getProducts();

    ProductDto getProductById(String id);

    List<ProductDto> getProductsByCategory(String category);
}
