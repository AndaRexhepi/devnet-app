package org.example.devnet.user.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserProfileDto;
import org.example.devnet.user.mappers.UserProfileMapper;
import org.example.devnet.user.repositories.UserRepository;
import org.example.devnet.user.services.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    public final UserRepository userRepository;
    public final UserProfileMapper userProfileMapper;


    @Override
    public List<UserProfileDto> findAll() {
        return userProfileMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserProfileDto findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userProfileMapper.toDto(userRepository.findById(id).get());
        }else {
            throw new EntityNotFoundException("User not found");
        }

    }

    @Override
    public UserProfileDto add(UserProfileDto entity) {
        if (userRepository.findById(entity.getId()).isPresent()) {
            return userProfileMapper.toDto(userRepository.findById(entity.getId()).get());
        }else {
            throw new EntityNotFoundException("User not found");
        }

    }

    @Override
    public UserProfileDto modify(UserProfileDto userProfileDto, Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userProfileMapper.toDto(userRepository.findById(id).get());
        }else {
            throw new EntityNotFoundException("User not found");
        }

    }

    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("User not found");
        }

    }

    @Override
    public UserProfileDto findByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return userProfileMapper.toDto(userRepository.findByUsername(username).get());
        }else {
            throw new EntityNotFoundException("User not found");
        }
    }
}
