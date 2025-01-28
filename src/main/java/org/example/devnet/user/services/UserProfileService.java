package org.example.devnet.user.services;

import org.example.devnet.user.dtos.UserProfileDto;

public interface UserProfileService extends BaseService<UserProfileDto, Long> {
    UserProfileDto findByUsername(String username);


}
