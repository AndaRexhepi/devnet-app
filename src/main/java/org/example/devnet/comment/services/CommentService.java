package org.example.devnet.comment.services;

import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.user.services.BaseService;

import java.util.List;

public interface CommentService extends BaseService<CommentDto, Long> {
    List<CommentDto> findAllByPostId(long postId);
}
