package org.example.devnet.post.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.repositories.CommentRepository;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.mappers.PostMapper;
import org.example.devnet.post.models.Post;
import org.example.devnet.post.repositories.PostRepository;
import org.example.devnet.post.services.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
public class PostServiceImpl implements PostService {

    public final PostRepository postRepository;
    public final CommentRepository commentRepository;
    public final PostMapper postMapper;

    @Override
    public List<PostDto> findAll() {
        var posts = postRepository.findAll();
        return postMapper.toDtoList(posts);
    }

    @Override
    public PostDto findById(Long id) {
        if (postRepository.findById(id).isPresent()) {
            return postMapper.toDto(postRepository.findById(id).get());
        } else {
            throw new EntityNotFoundException();
        }

    }

    @Override
    public PostDto add(PostDto entity) {
        var post = postMapper.toEntity(entity);
        postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public PostDto modify(PostDto postDto, Long id) {
        if (postRepository.findById(id).isPresent()) {
            var entity = postMapper.toEntity(postDto);
            postRepository.save(entity);
            return postMapper.toDto(entity);
        } else {
            throw new EntityNotFoundException();
        }

    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (postRepository.findById(id).isPresent()) {
            commentRepository.deleteByPostId(id);
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }

    }

    @Override
    public List<Post> findByCommunityId(Long id) {
        return postRepository.findByCommunityId(id);
    }

    @Override
    public PostDto incrementLikes(Long postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public List<Post> findByUsernameId(Long userId) {
        return postRepository.findByUsernameId(userId);
    }
}
