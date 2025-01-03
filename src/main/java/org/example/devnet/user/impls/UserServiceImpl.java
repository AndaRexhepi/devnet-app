package org.example.devnet.user.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserDto;
import org.example.devnet.user.mappers.UserMapper;
import org.example.devnet.user.repositories.UserRepository;
import org.example.devnet.user.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    public final UserMapper userMapper;


    @Override
    public List<UserDto> findAll() {
        var users = userRepository.findAll();
        return userMapper.toDtoList(users);

    }

    @Override
    public UserDto findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userMapper.toDto(userRepository.findById(id).get());
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public UserDto add(UserDto entity) {
        var user = userMapper.toEntity(entity);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto modify(UserDto userDto) {
        if (userRepository.findById(userDto.getId()).isPresent()) {
            var user = userMapper.toEntity(userDto);
            userRepository.save(user);
            return userMapper.toDto(user);
        }else {
            throw new EntityNotFoundException("User not found");
        }

    }

    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}
