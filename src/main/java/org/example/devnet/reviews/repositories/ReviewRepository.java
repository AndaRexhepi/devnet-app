package org.example.devnet.reviews.repositories;

import org.example.devnet.user.models.User;
import org.example.devnet.reviews.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
