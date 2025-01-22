package org.example.devnet.user.mappers;

import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper extends BaseMapper<UserRegistrationDto, User> {

    // if the source and target attributes are the same name and type then we don't need to specify them

    UserRegistrationDto toDto(User entity);


}
