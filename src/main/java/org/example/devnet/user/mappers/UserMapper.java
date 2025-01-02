package org.example.devnet.user.mappers;

import org.example.devnet.user.dtos.UserDto;
import org.example.devnet.user.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {

    // if the source and target attributes are the same name and type then we don't need to specify them

    UserDto toDto(User entity);
}
