package org.example.devnet.community.mappers;

import org.example.devnet.user.mappers.BaseMapper;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.models.Community;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityMapper extends BaseMapper<CommunityDto, Community> {

    CommunityDto toDto(Community community);
}
