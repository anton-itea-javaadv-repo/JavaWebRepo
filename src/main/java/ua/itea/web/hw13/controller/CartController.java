package ua.itea.web.hw13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.itea.web.hw13.dto.ProductDto;
import ua.itea.web.hw13.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ProductService prodService;

    public CartController(ProductService prodService) {
        this.prodService = prodService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpSession session) {
        Map<ProductDto, Integer> productsCartMap =
                (Map<ProductDto, Integer>) session.getAttribute("productsCartMap");
        if (productsCartMap == null) {
            productsCartMap = new LinkedHashMap<>();
        }
        session.setAttribute("productMap", productsCartMap);
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String doPost(
            HttpSession session,
            @RequestParam(name = "buy", required = false) Integer id,
            @RequestParam(name = "change", required = false) Integer changeId,
            @RequestParam(name = "quantity") String quantityStr) {
        Integer quantity = Integer.parseInt(quantityStr);
        if (changeId != null) {
            id = changeId;
        }

//        ProductDao pds = new MySqlProductDao(DbConnector.getInstance());
        Map<ProductDto, Integer> productsCartMap = null;

        ProductDto product = prodService.getProductById(id);
        productsCartMap = (Map<ProductDto, Integer>) session.getAttribute("productsCartMap");
        if (productsCartMap == null) {
            productsCartMap = new LinkedHashMap<>();
        }
        Integer count = productsCartMap.get(product);
        boolean removedProductFromCart = false;

        if (changeId != null) {
            if (quantity > 0) {
                productsCartMap.put(product, quantity);
            } else {
                productsCartMap.remove(product);
                removedProductFromCart = true;
            }
        } else if (id != null) {
            if (count == null) {
                productsCartMap.put(product, quantity);
            } else {
                productsCartMap.put(product, count + quantity);
            }
        }

        session.setAttribute("productsCartMap", productsCartMap);

        int countProducts = !productsCartMap.entrySet().isEmpty()
                ? productsCartMap.entrySet().stream().map(Map.Entry::getValue).reduce(Integer::sum).get()
                : 0;
        int sumProductPrices = !productsCartMap.entrySet().isEmpty()
                ? productsCartMap.entrySet().stream()
                        .map(e -> e.getKey().getPrice()*e.getValue()).reduce(Integer::sum).get()
                : 0;

        session.setAttribute("productsCartListSize", countProducts);
        session.setAttribute("sumProductPrices", sumProductPrices);
        if (changeId != null) {
            //тут выводим количество продуктов в корзине плюс состояние продукта в корзине, убрали ли мы его из корзины
            return "["+countProducts+", "+sumProductPrices+", "+removedProductFromCart+"]";
        } else {
            //тут выводим количество продуктов в корзине, это для products.
            return ""+countProducts;
        }
    }
}
