package ua.itea.web.hw12.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@Component
public class DbConnector {
    public static final Logger LOG = Logger.getLogger(DbConnector.class.getName());

    private static DbConnector instance;
    private DbConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            LOG.severe("Can't initialize Mysql driver: " + ex.getMessage());
        }
    }

    public static DbConnector getInstance() {
        if (instance == null) {
            instance = new DbConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=");
        } catch (SQLException ex) {
            // handle any errors
            LOG.severe("SQLException: " + ex.getMessage());
            LOG.severe("SQLState: " + ex.getSQLState());
            LOG.severe("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
}
