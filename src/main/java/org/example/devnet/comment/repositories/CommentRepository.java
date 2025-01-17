package org.example.devnet.comment.repositories;

import org.example.devnet.comment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(long postId);

    void deleteByPostId(Long postId);



}
