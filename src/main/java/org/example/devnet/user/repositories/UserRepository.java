package org.example.devnet.user.repositories;

import org.example.devnet.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findAllByUsernameContains(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}