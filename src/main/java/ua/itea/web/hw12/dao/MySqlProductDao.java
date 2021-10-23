package ua.itea.web.hw12.dao;

import org.springframework.stereotype.Repository;
import ua.itea.web.hw12.model.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ua.itea.web.hw12.util.StaticUtils.closeQuietly;

@Repository
public class MySqlProductDao implements ProductDao {
    public static final Logger LOG = Logger.getLogger(MySqlProductDao.class.getName());

    public static final String SELECT_PRODUCTS = "SELECT p.id, p.name, p.description, p.price, p2c.category_id, c.name " +
            "FROM `products` p " +
            "JOIN `product2category` p2c on p2c.product_id = p.id " +
            "JOIN `categories` c on c.id = p2c.category_id ";
    public static final String SELECT_PRODUCTS_BY_ID =
            SELECT_PRODUCTS +
                    "WHERE p.id = ?";
    public static final String SELECT_PRODUCTS_BY_CATEGORY =
            SELECT_PRODUCTS +
                    "WHERE c.name = ?";

    private final DbConnector dc;

    public MySqlProductDao(DbConnector dc) {
        this.dc = dc;
    }

    @Override
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
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return result;
    }

    @Override
    public ProductDto getProductById(String id) {
        Connection conn = dc.getConnection();
        ProductDto result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            if (id != null && !id.isEmpty()) {
                ps = conn.prepareStatement(SELECT_PRODUCTS_BY_ID);
                ps.setString(1, id);
                rs = ps.executeQuery();
            }
            while (rs != null && rs.next()) {
                ProductDto pd = new ProductDto();
                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setDescription(rs.getString(3));
                pd.setPrice(rs.getInt(4));
                result = pd;
            }
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return result;
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        Connection conn = dc.getConnection();
        List<ProductDto> result = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            if (category != null && !category.isEmpty()) {
                ps = conn.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY);
                ps.setString(1, category);
            } else {
                ps = conn.prepareStatement(SELECT_PRODUCTS);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDto pd = new ProductDto();
                pd.setId(rs.getInt(1));
                pd.setName(rs.getString(2));
                pd.setDescription(rs.getString(3).substring(0, 30) + "...");
                pd.setPrice(rs.getInt(4));
                result.add(pd);
            }
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return result;
    }
}
