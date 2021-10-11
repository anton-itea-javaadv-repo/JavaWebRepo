package ua.itea.hw08.service;

import ua.itea.hw08.dao.DbConnector;
import ua.itea.hw08.model.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDbService {
    public static final Logger LOG = Logger.getLogger(ProductDbService.class.getName());
    private DbConnector dc;
    public static final String SELECT_PRODUCTS = "SELECT id, name, description, price FROM products";

    public ProductDbService(DbConnector dc) {
        this.dc = dc;
    }

    public List<ProductDto> getProducts() {
        Connection conn = dc.getConnection();
        List<ProductDto> result = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(SELECT_PRODUCTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDto pd = new ProductDto();
                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setDescription(rs.getString(3));
                pd.setPrice(rs.getInt(4));
                result.add(pd);
            }
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "DB error: " + e.getMessage(), e);
            try {
                rs.close();
            } catch (SQLException e1) {}
            try {
                ps.close();
            } catch (SQLException e1) {}
            try {
                conn.close();
            } catch (SQLException e1) {}
        }
        return result;
    }
}
