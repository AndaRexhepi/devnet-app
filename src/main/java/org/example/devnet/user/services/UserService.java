package org.example.devnet.user.services;

import org.example.devnet.user.dtos.UserDto;
import org.example.devnet.user.exceptions.EmailExists;
import org.example.devnet.user.exceptions.UsernameExists;


public interface UserService extends BaseService<UserDto, Long> {


    UserDto registerUser(UserDto userDto) throws UsernameExists, EmailExists;

    UserDto login(String username, String password);


}
