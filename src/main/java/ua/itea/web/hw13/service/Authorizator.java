package ua.itea.web.hw13.service;

import org.springframework.stereotype.Service;
import ua.itea.web.hw13.dto.UserDto;
import ua.itea.web.hw13.util.StaticUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class Authorizator {
    private final UserService userService;

    public Authorizator(UserService userService) {
        this.userService = userService;
    }

    public String isAuthorized(String login, String password) {
        Optional<UserDto> userOpn = userService.get(login);
        if (userOpn.isPresent()) {
            UserDto user = userOpn.get();
            if (Objects.equals(StaticUtils.getSaltedHashedPassword(password), user.getPassword())) {
                return user.getName();
            }
        }
        return null;
    }
}
