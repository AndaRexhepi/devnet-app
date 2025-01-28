package org.example.devnet.user.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.exceptions.EmailExists;
import org.example.devnet.user.exceptions.UsernameExists;
import org.example.devnet.user.exceptions.WrongPassword;
import org.example.devnet.user.mappers.UserRegistrationMapper;
import org.example.devnet.user.repositories.UserRepository;
import org.example.devnet.user.services.UserRegistrationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    public final UserRepository userRepository;
    public final UserRegistrationMapper userRegistrationMapper;
    public final PasswordEncoder passwordEncoder;

    @Override
    public List<UserRegistrationDto> findAll() {
        var users = userRepository.findAll();
        return userRegistrationMapper.toDtoList(users);

    }

    @Override
    public UserRegistrationDto findById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRegistrationMapper.toDto(userRepository.findById(id).get());
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    public UserRegistrationDto add(UserRegistrationDto entity) {
        var user = userRegistrationMapper.toEntity(entity);
        userRepository.save(user);
        return userRegistrationMapper.toDto(user);
    }

    @Override
    public UserRegistrationDto modify(UserRegistrationDto userDto, Long id) {
        if (userRepository.findById(id).isPresent()) {
            var user = userRegistrationMapper.toEntity(userDto);
            userRepository.save(user);
            return userRegistrationMapper.toDto(user);
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


    @Override
    public UserRegistrationDto registerUser(UserRegistrationDto userDto) throws UsernameExists, EmailExists {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UsernameExists("Username already exists");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailExists("Email already exists");
        }
        var user = userRegistrationMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
        if (userRepository.count() == 0) {
            user.setRole("ADMIN");
        }else {
            user.setRole("USER");
        }
        userRepository.save(user);
        return userRegistrationMapper.toDto(user);
    }

    @Override
    public UserRegistrationDto login(String username, String password) {
        var user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return userRegistrationMapper.toDto(user.get());
            } else {
                throw new WrongPassword("Invalid email or password!");
            }
        } else {
            throw new EntityNotFoundException("User does not exist!");
        }
    }
}
