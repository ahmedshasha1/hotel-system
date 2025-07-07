package code.hotelservice.hotelservice.service.mapper;

import code.hotelservice.hotelservice.dto.auth.UserDto;
import code.hotelservice.hotelservice.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);
    UserDto toDto(User user);
    List<User> toEntityList(List<UserDto> userDtos);
    List<UserDto> toDtoList(List<User> userD);

}
