package ua.itea.hw08.dao;

import ua.itea.hw08.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ua.itea.hw08.util.StaticUtils.getSaltedHashedPassword;

public class MySqlUserDao implements Dao<User> {
    public static final String SELECT_ALL_USERS = "SELECT name, login, password, gender, region, comment " +
            "FROM users ";
    public static final String SELECT_USER = SELECT_ALL_USERS +
            "WHERE login=?";
    public static final String INSERT_USER = "INSERT INTO users (name, login, password, gender, region, comment) " +
            "VALUES(?,?,?,?,?,?)";
    public static final String UPDATE_USER = "UPDATE users SET name=?, login=?, password=?, gender=?, region=?, comment=? " +
            "WHERE login=?";
    public static final String DELETE_USER = "DELETE users WHERE login=?";

    @Override
    public Optional<User> get(String login) {
        User user = null;
        Connection conn = DbConnector.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SELECT_USER);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setRegion(rs.getString(5));
                user.setComment(rs.getString(6));
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        Connection conn = DbConnector.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(SELECT_ALL_USERS);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setName(rs.getString(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setGender(rs.getString(4));
                user.setRegion(rs.getString(5));
                user.setComment(rs.getString(6));
                userList.add(user);
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
        return userList;
    }

    @Override
    public void save(User user) {
        Connection conn = DbConnector.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(INSERT_USER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, getSaltedHashedPassword(user.getPassword()));
            ps.setString(4, user.getGender());
            ps.setString(5, user.getRegion());
            ps.setString(6, user.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
    }

    @Override
    public void update(User user, String oldLogin) {
        Connection conn = DbConnector.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, getSaltedHashedPassword(user.getPassword()));
            ps.setString(4, user.getGender());
            ps.setString(5, user.getRegion());
            ps.setString(6, user.getComment());
            ps.setString(7, oldLogin);
            ps.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
    }

    @Override
    public void delete(User user) {
        Connection conn = DbConnector.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(DELETE_USER);
            ps.setString(1, user.getLogin());
            ps.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {

            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
    }
}
