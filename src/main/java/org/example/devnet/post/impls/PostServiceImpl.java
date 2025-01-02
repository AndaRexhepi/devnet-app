package org.example.devnet.post.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.mappers.PostMapper;
import org.example.devnet.post.repositories.PostRepository;
import org.example.devnet.post.services.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    public final PostRepository postRepository;
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
    public PostDto modify(PostDto postDto) {
        if (postRepository.findById(postDto.getId()).isPresent()) {
            var entity = postMapper.toEntity(postDto);
            return postMapper.toDto(entity);
        } else {
            throw new EntityNotFoundException();
        }

    }

    @Override
    public void delete(Long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }

    }
}
