package org.example.devnet.comment.mappers;

import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.models.Comment;
import org.example.devnet.user.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<CommentDto, Comment> {

    CommentDto toDto(Comment comment);
}
