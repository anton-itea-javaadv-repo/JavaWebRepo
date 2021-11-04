package ua.itea.web.hw14.lesson14hw.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/products")
public interface ProductController {
    @RequestMapping(method = RequestMethod.GET)
    String get(
            ModelMap model,
            @RequestParam(name = "category", required = false) String cat,
            @RequestParam(required = false) String id);
}
