package ua.itea.web.hw14.lesson14hw.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.web.hw14.lesson14hw.controller.ProductController;
import ua.itea.web.hw14.lesson14hw.dto.ProductDto;
import ua.itea.web.hw14.lesson14hw.service.ProductService;

import java.util.List;

@Controller
public class ProductControllerImpl implements ProductController {
    private final ProductService prodService;

    public ProductControllerImpl(ProductService prodService) {
        this.prodService = prodService;
    }

    @Override
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
