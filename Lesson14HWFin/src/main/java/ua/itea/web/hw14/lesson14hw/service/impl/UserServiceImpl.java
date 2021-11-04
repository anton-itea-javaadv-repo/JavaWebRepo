package ua.itea.web.hw14.lesson14hw.service.impl;

import org.springframework.stereotype.Service;
import ua.itea.web.hw14.lesson14hw.component.UserMapper;
import ua.itea.web.hw14.lesson14hw.dto.UserDto;
import ua.itea.web.hw14.lesson14hw.model.UserEntity;
import ua.itea.web.hw14.lesson14hw.repository.UserRepository;
import ua.itea.web.hw14.lesson14hw.util.StaticUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements ua.itea.web.hw14.lesson14hw.service.UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDto> get(String login) {
        return Optional.ofNullable(userMapper.entityToDto(userRepository.findByLogin(login)));
    }

    @Override
    public void save(UserDto user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userEntity.setPassword(StaticUtils.getSaltedHashedPassword(user.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public void update(UserDto user, String oldLogin) {
        UserEntity userEntity = userRepository.findByLogin(oldLogin);
        if (userEntity != null) {
            userEntity.setPassword(StaticUtils.getSaltedHashedPassword(user.getPassword()))
                    .setName(user.getName())
                    .setLogin(user.getLogin())
                    .setGender(user.getGender())
                    .setRegion(user.getRegion())
                    .setComment(user.getComment());
            userRepository.save(userEntity);
        }
    }

    @Override
    public void delete(UserDto user) {
        UserEntity userEntity = userRepository.findByLogin(user.getLogin());
        if (userEntity != null) {
            userRepository.delete(userEntity);
        }
    }
}
