package ua.itea.web.hw13.service;

import org.springframework.stereotype.Service;
import ua.itea.web.hw13.component.UserEntityManager;
import ua.itea.web.hw13.component.UserMapper;
import ua.itea.web.hw13.dto.UserDto;
import ua.itea.web.hw13.model.UserEntity;
import ua.itea.web.hw13.util.StaticUtils;

import java.util.Optional;

@Service
public class UserService {
    private final UserEntityManager userEm;
    private final UserMapper userMapper;

    public UserService(UserEntityManager userEm, UserMapper userMapper) {
        this.userEm = userEm;
        this.userMapper = userMapper;
    }

    public Optional<UserDto> get(String login) {
        return Optional.ofNullable(userMapper.entityToDto(userEm.getUserByLogin(login)));
    }

    public void save(UserDto user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userEntity.setPassword(StaticUtils.getSaltedHashedPassword(user.getPassword()));
        userEm.saveUser(userEntity);
    }

    public void update(UserDto user, String oldLogin) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userEntity.setPassword(StaticUtils.getSaltedHashedPassword(user.getPassword()));
        userEm.updateUser(userEntity, oldLogin);
    }

    public void delete(UserDto user) {
        userEm.deleteUser(user.getLogin());
    }
}
