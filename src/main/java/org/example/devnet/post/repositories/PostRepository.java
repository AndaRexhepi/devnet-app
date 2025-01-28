package org.example.devnet.post.repositories;

import org.example.devnet.post.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCommunityId(Long communityId);

    List<Post> findByUsernameId(Long userId);

    List<Post> findAllByOrderByIdDesc();

}
