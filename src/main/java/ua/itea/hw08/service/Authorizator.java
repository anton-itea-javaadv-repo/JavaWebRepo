package ua.itea.hw08.service;

import ua.itea.hw08.dao.MySqlUserDao;
import ua.itea.hw08.domain.User;

import java.util.Objects;
import java.util.Optional;

import static ua.itea.hw08.util.StaticUtils.getSaltedHashedPassword;

public class Authorizator {
    public String isAuthorized(String login, String password) {
        MySqlUserDao dao = new MySqlUserDao();
        Optional<User> userOpn = dao.get(login);
        if (userOpn.isPresent()) {
            User user = userOpn.get();
            if (Objects.equals(getSaltedHashedPassword(password), user.getPassword())) {
                return user.getName();
            }
        }
        return null;
    }
}
