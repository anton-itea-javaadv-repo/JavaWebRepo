package ua.itea.web.hw14p2.lesson14p2hw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.itea.web.hw14p2.lesson14p2hw.dto.CartDto;

@RequestMapping("/cartcontroller")
public interface CartController {
    @GetMapping
    ResponseEntity<CartDto> get(@RequestParam Integer id);
    @PostMapping
    ResponseEntity<CartDto> create(@RequestBody CartDto cart);
    @PutMapping
    ResponseEntity<CartDto> update(@RequestBody CartDto cart);
    @DeleteMapping
    ResponseEntity delete(@RequestParam Integer id);
}
