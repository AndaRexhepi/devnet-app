package org.example.devnet.api_v1.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserDto;
import org.example.devnet.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccApiController {
    public final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/default")
    public UserDto getDefaultUser() {
        return new UserDto();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public UserDto add(@Valid @RequestBody UserDto userDto) {
        return userService.add(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public UserDto modify(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        return userService.modify(userDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        userService.delete(id);
    }



}
