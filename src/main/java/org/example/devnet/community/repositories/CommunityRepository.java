package org.example.devnet.community.repositories;

import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.models.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {


    Optional<Community> findByName(String name);

    List<Community> findAllByNameIsContaining(String name);

    List<Community> findByMembers_Id(Long id);

    List<Community> findAllByOwnerId(Long id);
}
