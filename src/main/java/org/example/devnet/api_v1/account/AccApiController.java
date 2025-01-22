package org.example.devnet.api_v1.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.services.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccApiController {
    public final UserRegistrationService userService;

    @GetMapping
    public List<UserRegistrationDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserRegistrationDto findById(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/default")
    public UserRegistrationDto getDefaultUser() {
        return new UserRegistrationDto();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public UserRegistrationDto add(@Valid @RequestBody UserRegistrationDto userDto) {
        return userService.add(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public UserRegistrationDto modify(@Valid @RequestBody UserRegistrationDto userDto, @PathVariable Long id) {
        return userService.modify(userDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        userService.delete(id);
    }



}
