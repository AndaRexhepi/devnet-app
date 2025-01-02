package org.example.devnet.projectshowcase.repositories;

import org.example.devnet.user.models.User;
import org.example.devnet.projectshowcase.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    List<Project> findByUsername(User username);

    List<Project> findByDescriptionContaining(String keyword);

    List<Project> findByImageUrl(String imageUrl);
}
