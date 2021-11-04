package ua.itea.web.hw14p2.lesson14p2hw.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.itea.web.hw14p2.lesson14p2hw.controller.CartController;
import ua.itea.web.hw14p2.lesson14p2hw.dto.CartDto;
import ua.itea.web.hw14p2.lesson14p2hw.service.CartService;

import java.util.Optional;

@RestController
public class CartControllerImpl implements CartController {
    private final CartService service;

    public CartControllerImpl(final CartService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<CartDto> get(Integer id) {
        Optional<CartDto> pudgeOpn = service.get(id);
        if (pudgeOpn.isPresent()) {
            return new ResponseEntity<>(pudgeOpn.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<CartDto> create(CartDto cart) {
        return new ResponseEntity<>(service.save(cart), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CartDto> update(CartDto cart) {
        return new ResponseEntity<>(service.update(cart, cart.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
