package org.example.devnet.community.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.mappers.CommunityMapper;
import org.example.devnet.community.models.Community;
import org.example.devnet.community.repositories.CommunityRepository;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.models.Post;
import org.example.devnet.post.repositories.PostRepository;
import org.example.devnet.user.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {


    public final CommunityRepository communityRepository;
    public final PostRepository postRepository;
    public final CommunityMapper communityMapper;

    @Override
    public List<CommunityDto> findAll() {
        var communities = communityRepository.findAll();
        return communityMapper.toDtoList(communities);
    }

    @Override
    public CommunityDto findById(Long id) {
        if(communityRepository.findById(id).isPresent()){
            return communityMapper.toDto(communityRepository.findById(id).get());
        }else {
            throw new EntityNotFoundException();
        }

    }

    @Override
    public CommunityDto add(CommunityDto entity) {
        var communityEntity = communityMapper.toEntity(entity);
        communityRepository.save(communityEntity);
        return communityMapper.toDto(communityEntity);
    }

    @Override
    public CommunityDto modify(CommunityDto communityDto, Long id) {
        if (communityRepository.findById(id).isPresent()){
            var entity = communityMapper.toEntity(communityDto);
            communityRepository.save(entity);
            return communityMapper.toDto(entity);
        }else {
            throw new EntityNotFoundException();
        }

    }

    @Transactional
    public void delete(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found"));
        List<Post> posts = postRepository.findByCommunityId(communityId);
        for (Post post : posts) {
            post.setCommunity(null);
        }
        postRepository.saveAll(posts);

        communityRepository.delete(community);
    }

    @Override
    public CommunityDto findByName(String communityName) {
        if(communityRepository.findByName(communityName).isPresent()){
            return communityMapper.toDto(communityRepository.findByName(communityName).get());
        }else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<CommunityDto> findAllByOwnerId(Long id) {
        var communities = communityRepository.findAllByOwnerId(id);
        return communityMapper.toDtoList(communities);
    }


    @Transactional
    public void addMemberToCommunity(Long communityId, User user) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("Community not found"));

        // Check if the user is already a member
        if (community.getMembers().contains(user)) {
            throw new IllegalStateException("User is already a member of this community.");
        }

        // Add the user to the community's list of members
        community.addMember(user);
        communityRepository.save(community);
    }

}
