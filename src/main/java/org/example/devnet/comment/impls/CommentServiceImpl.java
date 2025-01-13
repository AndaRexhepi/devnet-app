package org.example.devnet.comment.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.mappers.CommentMapper;
import org.example.devnet.comment.repositories.CommentRepository;
import org.example.devnet.comment.services.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public final CommentRepository commentRepository;
    public final CommentMapper commentMapper;

    @Override
    public List<CommentDto> findAllByPostId(long postId) {
        return commentMapper.toDtoList(commentRepository.findAllByPostId(postId));
    }

    @Override
    public List<CommentDto> findAll() {
        return commentMapper.toDtoList(commentRepository.findAll());
    }

    @Override
    public CommentDto findById(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            return commentMapper.toDto(commentRepository.findById(id).get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public CommentDto add(CommentDto entity) {
        var commentEntity = commentMapper.toEntity(entity);
        commentRepository.save(commentEntity);
        return commentMapper.toDto(commentEntity);
    }

    @Override
    public CommentDto modify(CommentDto commentDto, Long id) {
        if (commentRepository.findById(id).isPresent()) {
            var entity = commentMapper.toEntity(commentDto);
            commentRepository.save(entity);
            return commentMapper.toDto(entity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
