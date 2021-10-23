package ua.itea.web.hw12.service;

import org.springframework.stereotype.Service;
import ua.itea.web.hw12.dao.Dao;
import ua.itea.web.hw12.model.UserDto;
import ua.itea.web.hw12.util.StaticUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class Authorizator {
    private final Dao<UserDto> userDao;

    public Authorizator(Dao<UserDto> dao) {
        this.userDao = dao;
    }

    public String isAuthorized(String login, String password) {
        Optional<UserDto> userOpn = userDao.get(login);
        if (userOpn.isPresent()) {
            UserDto user = userOpn.get();
            if (Objects.equals(StaticUtils.getSaltedHashedPassword(password), user.getPassword())) {
                return user.getName();
            }
        }
        return null;
    }
}
