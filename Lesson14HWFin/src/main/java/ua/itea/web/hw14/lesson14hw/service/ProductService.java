package ua.itea.web.hw14.lesson14hw.service;

import ua.itea.web.hw14.lesson14hw.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(int id);
    List<ProductDto> getProductsByCategory(String categoryName);
}
