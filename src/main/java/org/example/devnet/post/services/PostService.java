package org.example.devnet.post.services;

import org.example.devnet.post.models.Post;
import org.example.devnet.user.services.BaseService;
import org.example.devnet.post.dtos.PostDto;

import java.util.List;

public interface PostService extends BaseService<PostDto, Long> {
    List<Post> findByCommunityId(Long id);
}
