package org.example.devnet.user.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.services.PostService;
import org.example.devnet.user.dtos.UserDto;
import org.example.devnet.user.exceptions.EmailExists;
import org.example.devnet.user.exceptions.UsernameExists;
import org.example.devnet.user.mappers.UserMapper;
import org.example.devnet.user.models.User;
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
    public final CommunityService communityService;
    public final PostService postService;
    public final UserMapper userMapper;

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
            userService.registerUser(userDto);
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
        model.addAttribute("userDto", new UserDto());
        return "account/log_in";
    }

    @PostMapping("log_in")
    public String login(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult,
                        HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "account/log_in";
        }

        try {
            var user = userService.login(userDto.getUsername(), userDto.getPassword());
            Cookie cookie = new Cookie("user", user.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            System.out.println(session.getId());
            return "redirect:/";
        } catch (Exception e) {
            bindingResult.rejectValue("username", "error.userDto", e.getMessage());
            return "account/log_in";
        }
    }


    @GetMapping("profile")
    public String profile(Model model, HttpServletRequest request) {
        User user = userMapper.toEntity((UserDto) request.getSession().getAttribute("user"));
        UserDto userDto = userMapper.toDto(user);
        model.addAttribute("user", userDto);
        model.addAttribute("posts", postService.findByUsernameId(user.getId()));
        model.addAttribute("communities", communityService.findAllByOwnerId(user.getId()));
        return "account/profile";
    }

    @GetMapping("test")
    public String changePassword(Model model) {
        return "test";
    }
}
