package ua.itea.scratchesl10.service;

import ua.itea.scratchesl10.dao.MySqlUserDao;
import ua.itea.scratchesl10.model.UserDto;

import java.util.Objects;
import java.util.Optional;

import static ua.itea.scratchesl10.util.StaticUtils.getSaltedHashedPassword;

public class Authorizator {
    public String isAuthorized(String login, String password) {
        MySqlUserDao dao = new MySqlUserDao();
        Optional<UserDto> userOpn = dao.get(login);
        if (userOpn.isPresent()) {
            UserDto user = userOpn.get();
            if (Objects.equals(getSaltedHashedPassword(password), user.getPassword())) {
                return user.getName();
            }
        }
        return null;
    }
}
