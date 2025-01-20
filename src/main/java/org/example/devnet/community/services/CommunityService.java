package org.example.devnet.community.services;

import org.example.devnet.community.models.Community;
import org.example.devnet.user.services.BaseService;
import org.example.devnet.community.dtos.CommunityDto;

import java.util.List;

public interface CommunityService extends BaseService<CommunityDto, Long> {
    CommunityDto findByName(String communityName);
    List<CommunityDto> findAllByOwnerId(Long id);
}
