package org.example.devnet.post.mappers;

import org.example.devnet.user.mappers.BaseMapper;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.models.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper extends BaseMapper<PostDto, Post> {

    PostDto toDto(Post entity);

}
