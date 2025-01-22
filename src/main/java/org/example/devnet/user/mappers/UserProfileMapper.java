package org.example.devnet.user.mappers;

import org.example.devnet.user.dtos.UserProfileDto;
import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseMapper<UserProfileDto, User> {
    UserProfileDto toDto(User entity);
    List<UserProfileDto> toDtoList(List<User> entityList);
    UserProfileDto toProfileDto(UserRegistrationDto entity);

}
