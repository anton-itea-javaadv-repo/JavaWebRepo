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
import java.io.IOException;
import java.util.List;

public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cat = req.getParameter("category");
        String id = req.getParameter("id");
        RequestDispatcher rd;
        ProductDao pds = new MySqlProductDao(DbConnector.getInstance());
        if (id != null && !id.isEmpty()) {
            rd = req.getRequestDispatcher("WEB-INF/views/product.jsp");
            ProductDto product = pds.getProductById(id);
            req.setAttribute("product", product);
        } else {
            rd = req.getRequestDispatcher("WEB-INF/views/products.jsp");
            List<ProductDto> products = pds.getProductsByCategory(cat);
            req.setAttribute("productList", products);
        }
        rd.forward(req, resp);
    }
}
