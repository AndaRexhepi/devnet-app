package org.example.devnet.user.controllers;

import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserDto;
import org.example.devnet.user.exceptions.EmailExists;
import org.example.devnet.user.exceptions.UsernameExists;
import org.example.devnet.user.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("sign_up")
    public String signup(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "account/sign_up";
    }

    @PostMapping("sign_up")
    public String signup(@Valid @ModelAttribute UserDto userDto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "account/sign_up";
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.userDto",
                    "Passwords do not match!");
            return "account/sign_up";
        }

        try {
            userService.add(userDto);
        } catch (UsernameExists e) {
            bindingResult.rejectValue("username", "error.userDto",
                    "Username already exists!");
            return "account/sign_up";
        } catch (EmailExists e) {
            bindingResult.rejectValue("email", "error.userDto",
                    "Email already exists!");
            return "account/sign_up";
        }

        model.addAttribute("userDto", new UserDto());
        return "account/log_in";
    }


    @GetMapping("log_in")
    public String login(Model model) {
        return "account/log_in";
    }


    @GetMapping("profile")
    public String profile(Model model) {
        return "account/profile";
    }

    @GetMapping("test")
    public String changePassword(Model model) {
        return "test";
    }
}
