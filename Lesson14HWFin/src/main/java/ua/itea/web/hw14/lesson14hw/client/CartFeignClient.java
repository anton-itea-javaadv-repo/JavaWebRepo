package ua.itea.web.hw14.lesson14hw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ua.itea.web.hw14.lesson14hw.dto.CartDto;

@FeignClient(value = "cartClient", url = "http://localhost:8084/cartcontroller/")
public interface CartFeignClient {
    @GetMapping
    CartDto get(@RequestParam Integer id);
    @PostMapping
    CartDto create(@RequestBody CartDto cart);
    @PutMapping
    CartDto update(@RequestBody CartDto cart);
    @DeleteMapping
    void delete(@RequestParam Integer id);
}
