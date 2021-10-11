package ua.itea.hw08.controller;

import ua.itea.hw08.dao.DbConnector;
import ua.itea.hw08.model.ProductDto;
import ua.itea.hw08.service.ProductDbService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/product.jsp");
        ProductDbService pds = new ProductDbService(DbConnector.getInstance());
        List<ProductDto> products = pds.getProducts();
        req.setAttribute("productList", products);
        rd.forward(req, resp);
    }
}
