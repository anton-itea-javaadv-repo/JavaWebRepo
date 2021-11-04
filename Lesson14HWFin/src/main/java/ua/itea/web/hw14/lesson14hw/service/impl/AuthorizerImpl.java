package ua.itea.web.hw14.lesson14hw.service.impl;

import org.springframework.stereotype.Service;
import ua.itea.web.hw14.lesson14hw.dto.UserDto;
import ua.itea.web.hw14.lesson14hw.util.StaticUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorizerImpl implements ua.itea.web.hw14.lesson14hw.service.Authorizer {
    private final UserServiceImpl userService;

    public AuthorizerImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
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
