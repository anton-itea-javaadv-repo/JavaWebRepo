package ua.itea.web.hw14p2.lesson14p2hw.service;

import ua.itea.web.hw14p2.lesson14p2hw.dto.CartDto;

import java.util.Optional;

public interface CartService {
    Optional<CartDto> get(int id);
    CartDto save(CartDto cart);
    CartDto update(CartDto cart, int id);
    void delete(int id);
}
