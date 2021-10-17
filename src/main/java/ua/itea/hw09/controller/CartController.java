package ua.itea.hw09.controller;

import ua.itea.hw09.dao.DbConnector;
import ua.itea.hw09.dao.MySqlProductDao;
import ua.itea.hw09.dao.ProductDao;
import ua.itea.hw09.model.ProductDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/cart.jsp");
        Map<ProductDto, Integer> productsCartMap = (Map<ProductDto, Integer>) session.getAttribute("productsCartMap");
        if (productsCartMap == null) {
            productsCartMap = new LinkedHashMap<>();
        }
        req.setAttribute("productMap", productsCartMap);
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("buy");
        String changeId = req.getParameter("change");
        String quantityStr = req.getParameter("quantity");
        Integer quantity = Integer.parseInt(quantityStr);
        if (changeId != null) {
            id = changeId;
        }
        HttpSession session = req.getSession();
        ProductDao pds = new MySqlProductDao(DbConnector.getInstance());
        Map<ProductDto, Integer> productsCartMap = null;

        ProductDto product = pds.getProductById(id);
        productsCartMap = (Map<ProductDto, Integer>) session.getAttribute("productsCartMap");
        if (productsCartMap == null) {
            productsCartMap = new LinkedHashMap<>();
        }
        Integer count = productsCartMap.get(product);

        if (changeId != null && !changeId.isEmpty()) {
            if (quantity > 0) {
                productsCartMap.put(product, quantity);
            } else {
                productsCartMap.remove(product);
            }
        } else if (id != null && !id.isEmpty()) {
            if (count == null) {
                productsCartMap.put(product, quantity);
            } else {
                productsCartMap.put(product, count + quantity);
            }
        }

        session.setAttribute("productsCartMap", productsCartMap);

        int countProducts = !productsCartMap.entrySet().isEmpty()
                ? productsCartMap.entrySet().stream().map(Map.Entry::getValue).reduce((a, b) -> a + b).get()
                : 0;
        int sumProductPrices = !productsCartMap.entrySet().isEmpty()
                ? productsCartMap.entrySet().stream().map(e -> e.getKey().getPrice()*e.getValue()).reduce((a, b) -> a + b).get()
                : 0;

        session.setAttribute("productsCartListSize", countProducts);
        session.setAttribute("sumProductPrices", sumProductPrices);
        if (changeId != null && !changeId.isEmpty()) {
            resp.sendRedirect("cart");
        } else {
            resp.sendRedirect("products");
        }

    }
}
