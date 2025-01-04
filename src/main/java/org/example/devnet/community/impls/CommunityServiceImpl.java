package org.example.devnet.community.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.mappers.CommunityMapper;
import org.example.devnet.community.repositories.CommunityRepository;
import org.example.devnet.community.services.CommunityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {


    public final CommunityRepository communityRepository;
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

    @Override
    public void delete(Long id) {
        if (communityRepository.findById(id).isPresent()){
            communityRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException();
        }

    }
}
