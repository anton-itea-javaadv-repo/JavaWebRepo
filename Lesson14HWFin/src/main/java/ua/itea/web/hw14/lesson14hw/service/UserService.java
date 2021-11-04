package ua.itea.web.hw14.lesson14hw.service;

import ua.itea.web.hw14.lesson14hw.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> get(String login);
    void save(UserDto user);
    void update(UserDto user, String oldLogin);
    void delete(UserDto user);
}
