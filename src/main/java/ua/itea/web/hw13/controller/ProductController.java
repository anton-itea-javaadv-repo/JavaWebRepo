package ua.itea.web.hw13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.web.hw13.dto.ProductDto;
import ua.itea.web.hw13.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService prodService;

    public ProductController(ProductService prodService) {
        this.prodService = prodService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(
            ModelMap model,
            @RequestParam(name = "category", required = false) String cat,
            @RequestParam(required = false) String id) {
        String viewName;
        if (id != null && !id.isEmpty()) {
            viewName = "product";
            ProductDto product = prodService.getProductById(Integer.parseInt(id));
            model.addAttribute("product", product);
        } else {
            viewName = "products";
            List<ProductDto> products = prodService.getProductsByCategory(cat);
            model.addAttribute("productList", products);
        }
        return viewName;
    }
}
