package ua.itea.web.hw12.dao;

import org.springframework.stereotype.Repository;
import ua.itea.web.hw12.model.UserDto;
import ua.itea.web.hw12.util.StaticUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ua.itea.web.hw12.util.StaticUtils.closeQuietly;

@Repository
public class MySqlUserDao implements Dao<UserDto> {
    public static final Logger LOG = Logger.getLogger(MySqlUserDao.class.getName());

    public static final String SELECT_ALL_USERS = "SELECT name, login, password, gender, region, comment " +
            "FROM users ";
    public static final String SELECT_USER = SELECT_ALL_USERS +
            "WHERE login=?";
    public static final String INSERT_USER = "INSERT INTO users (name, login, password, gender, region, comment) " +
            "VALUES(?,?,?,?,?,?)";
    public static final String UPDATE_USER = "UPDATE users SET name=?, login=?, password=?, gender=?, region=?, comment=? " +
            "WHERE login=?";
    public static final String DELETE_USER = "DELETE users WHERE login=?";

    private final DbConnector dc;

    public MySqlUserDao(DbConnector dc) {
        this.dc = dc;
    }

    @Override
    public Optional<UserDto> get(String login) {
        UserDto user = null;
        Connection conn = dc.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(SELECT_USER);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserDto();
                user.setName(rs.getString(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setRegion(rs.getString(5));
                user.setComment(rs.getString(6));
            }
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userList = new ArrayList<>();
        Connection conn = dc.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(SELECT_ALL_USERS);
            rs = ps.executeQuery();
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setName(rs.getString(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setRegion(rs.getString(5));
                user.setComment(rs.getString(6));
                userList.add(user);
            }
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return userList;
    }

    @Override
    public void save(UserDto user) {
        Connection conn = dc.getConnection();
        PreparedStatement ps = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(INSERT_USER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, StaticUtils.getSaltedHashedPassword(user.getPassword()));
            ps.setString(4, user.getGender());
            ps.setString(5, user.getRegion());
            ps.setString(6, user.getComment());
            ps.executeUpdate();
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }

    @Override
    public void update(UserDto user, String oldLogin) {
        Connection conn = dc.getConnection();
        PreparedStatement ps = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, StaticUtils.getSaltedHashedPassword(user.getPassword()));
            ps.setString(4, user.getGender());
            ps.setString(5, user.getRegion());
            ps.setString(6, user.getComment());
            ps.setString(7, oldLogin);
            ps.executeUpdate();
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }

    @Override
    public void delete(UserDto user) {
        Connection conn = dc.getConnection();
        PreparedStatement ps = null;
        LOG.info("Starting query");
        try {
            ps = conn.prepareStatement(DELETE_USER);
            ps.setString(1, user.getLogin());
            ps.executeUpdate();
            LOG.info("Query success");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Query error: " + e.getMessage(), e);
        } finally {
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }
}
