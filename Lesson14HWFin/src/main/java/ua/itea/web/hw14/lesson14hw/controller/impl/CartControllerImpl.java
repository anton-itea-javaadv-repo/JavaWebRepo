package ua.itea.web.hw14.lesson14hw.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import ua.itea.web.hw14.lesson14hw.client.CartFeignClient;
import ua.itea.web.hw14.lesson14hw.controller.CartController;
import ua.itea.web.hw14.lesson14hw.dto.CartDto;
import ua.itea.web.hw14.lesson14hw.dto.ProductDto;
import ua.itea.web.hw14.lesson14hw.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class CartControllerImpl implements CartController {
    private final ProductService prodService;
    private final CartFeignClient cartClient;

    public CartControllerImpl(ProductService prodService, CartFeignClient cartClient) {
        this.prodService = prodService;
        this.cartClient = cartClient;
    }

    @Override
    public String doGet(HttpSession session, ModelMap model) {
        Integer cart_id = (Integer) session.getAttribute("cart_id");
        CartDto cartDto;
        if (cart_id == null) {
            cartDto = cartClient.create(new CartDto());
            cart_id = cartDto.getId();
        } else {
            cartDto = cartClient.get(cart_id);
        }
        final Map<ProductDto, Integer> productsCartMap = new LinkedHashMap<>();
        if (cartDto.getItems() != null && !cartDto.getItems().isEmpty()) {
            cartDto.getItems().forEach((k, v) -> productsCartMap.put(prodService.getProductById(k), v));
        }
        session.setAttribute("cart_id", cart_id);
        model.addAttribute("productMap", productsCartMap);
        return "cart";
    }

    @Override
    public String doPost(
            HttpSession session,
            ModelMap model,
            Integer id,
            Integer changeId,
            String quantityStr) {
        Integer quantity = Integer.parseInt(quantityStr);
        if (changeId != null) {
            id = changeId;
        }

        Integer cart_id = (Integer) session.getAttribute("cart_id");
        CartDto cartDto;
        if (cart_id == null) {
            cartDto = cartClient.create(new CartDto());
            cart_id = cartDto.getId();
        } else {
            cartDto = cartClient.get(cart_id);
        }

        Integer count = cartDto.getItems().get(id);
        boolean removedProductFromCart = false;

        if (changeId != null) {
            if (quantity > 0) {
                cartDto.getItems().put(id, quantity);
            } else {
                cartDto.getItems().remove(id);
                removedProductFromCart = true;
            }
        } else if (id != null) {
            if (count == null) {
                cartDto.getItems().put(id, quantity);
            } else {
                cartDto.getItems().put(id, count + quantity);
            }
        }
        cartDto = cartClient.update(cartDto);

        final Map<ProductDto, Integer> productsCartMap = new LinkedHashMap<>();
        if (cartDto.getItems() != null && !cartDto.getItems().isEmpty()) {
            cartDto.getItems().forEach((k, v) -> productsCartMap.put(prodService.getProductById(k), v));
        }

        session.setAttribute("cart_id", cart_id);

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
