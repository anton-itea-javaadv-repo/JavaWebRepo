package ua.itea.web.hw12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.web.hw12.dao.DbConnector;
import ua.itea.web.hw12.dao.MySqlProductDao;
import ua.itea.web.hw12.dao.ProductDao;
import ua.itea.web.hw12.model.ProductDto;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @RequestMapping(method = RequestMethod.GET)
    public String get(
            ModelMap model,
            @RequestParam(name = "category", required = false) String cat,
            @RequestParam(required = false) String id) {
        String viewName;
        ProductDao pds = new MySqlProductDao(DbConnector.getInstance());
        if (id != null && !id.isEmpty()) {
            viewName = "product";
            ProductDto product = pds.getProductById(id);
            model.addAttribute("product", product);
        } else {
            viewName = "products";
            List<ProductDto> products = pds.getProductsByCategory(cat);
            model.addAttribute("productList", products);
        }
        return viewName;
    }
}
