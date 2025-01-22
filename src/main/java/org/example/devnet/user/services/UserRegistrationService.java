package org.example.devnet.user.services;

import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.exceptions.EmailExists;
import org.example.devnet.user.exceptions.UsernameExists;


public interface UserRegistrationService extends BaseService<UserRegistrationDto, Long> {


    UserRegistrationDto registerUser(UserRegistrationDto userDto) throws UsernameExists, EmailExists;

    UserRegistrationDto login(String username, String password);


}

