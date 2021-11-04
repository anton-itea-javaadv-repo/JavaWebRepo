package ua.itea.web.hw14.lesson14hw.component;

import org.springframework.stereotype.Component;
import ua.itea.web.hw14.lesson14hw.dto.UserDto;
import ua.itea.web.hw14.lesson14hw.model.UserEntity;

@Component
public class UserMapper {
    public UserDto entityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto()
                .setName(entity.getName())
                .setLogin(entity.getLogin())
                .setPassword(entity.getPassword())
                .setGender(entity.getGender())
                .setRegion(entity.getRegion())
                .setComment(entity.getComment());
    }

    public UserEntity dtoToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserEntity()
                .setName(dto.getName())
                .setLogin(dto.getLogin())
                .setPassword(dto.getPassword())
                .setGender(dto.getGender())
                .setRegion(dto.getRegion())
                .setComment(dto.getComment());
    }
}
