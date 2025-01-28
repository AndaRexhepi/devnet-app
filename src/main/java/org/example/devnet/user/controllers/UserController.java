package org.example.devnet.user.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.services.PostService;
import org.example.devnet.projectshowcase.helpers.FileHelper;
import org.example.devnet.user.dtos.UserProfileDto;
import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.exceptions.EmailExists;
import org.example.devnet.user.exceptions.UsernameExists;
import org.example.devnet.user.mappers.UserProfileMapper;
import org.example.devnet.user.models.User;
import org.example.devnet.user.services.UserProfileService;
import org.example.devnet.user.services.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    public final UserRegistrationService userRegistrationService;
    public final UserProfileService userProfileService;
    public final CommunityService communityService;
    public final PostService postService;
    public final UserProfileMapper userProfileMapper;
    public final FileHelper fileHelper;

    @GetMapping("sign_up")
    public String signup(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "account/sign_up";
    }

    @PostMapping("sign_up")
    public String signup(@Valid @ModelAttribute UserRegistrationDto userDto,
                         BindingResult bindingResult, Model model, @RequestParam(value = "image", required = false) MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "account/sign_up";
        }

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                        file.getOriginalFilename(),
                        file.getBytes());
                userDto.setProfileImage("/assets/img/projects/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.userDto",
                    "Passwords do not match!");
            return "account/sign_up";
        }

        try {

            userRegistrationService.registerUser(userDto);
        } catch (UsernameExists e) {
            bindingResult.rejectValue("username", "error.userDto",
                    "Username already exists!");
            return "account/sign_up";
        } catch (EmailExists e) {
            bindingResult.rejectValue("email", "error.userDto",
                    "Email already exists!");
            return "account/sign_up";
        }

        model.addAttribute("userDto", new UserRegistrationDto());
        return "account/log_in";
    }


    @GetMapping("log_in")
    public String login(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "account/log_in";
    }

    @PostMapping("log_in")
    public String login(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto, BindingResult bindingResult,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            return "account/log_in";
        }

        try {
            var user = userRegistrationService.login(userDto.getUsername(), userDto.getPassword());
            Cookie cookie = new Cookie("user", user.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute("user", userProfileMapper.toProfileDto(user));
            System.out.println(session.getId());
            return "redirect:/";
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/log_in";
        }
    }

    @GetMapping("profile")
    public String profile(Model model, HttpServletRequest request) {

        UserProfileDto userDto = (UserProfileDto) request.getSession().getAttribute("user");

        User user = userProfileMapper.toEntity(userDto);


        model.addAttribute("user", userDto);
        model.addAttribute("posts", postService.findByUsernameId(user.getId()));
        model.addAttribute("communities", communityService.findAllByOwnerId(user.getId()));

        return "account/profile";
    }



    @GetMapping("log_out")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        request.getSession().invalidate();
        return "redirect:/log_in";
    }

    @GetMapping("edit_profile")
    public String editProfile(Model model, HttpServletRequest request) {
        UserProfileDto sessionUser = (UserProfileDto) request.getSession().getAttribute("user");
        System.out.println("Session user: " + sessionUser);

        if (sessionUser == null) {
            return "redirect:/login";
        }
        System.out.println("Session user ID: " + sessionUser.getId());
        model.addAttribute("user", sessionUser);
        return "account/edit_profile";
    }


@PostMapping("edit_profile/{id}")
public String editProfile(@Valid @ModelAttribute() UserProfileDto userDto, @PathVariable Long id , BindingResult bindingResult,
                          HttpServletRequest request, @RequestPart(value = "image", required = false) MultipartFile file) {
    if (bindingResult.hasErrors()) {
        return "account/edit_profile";
    }

    try {

        System.out.println("UserDto received: " + userDto);


        if (file != null && !file.isEmpty()) {
            String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                    file.getOriginalFilename(),
                    file.getBytes());
            userDto.setProfileImage("/assets/img/projects/" + fileName);
        } else {

            UserProfileDto existingUser = userProfileService.findById(id);
            if (existingUser != null) {
                userDto.setProfileImage(existingUser.getProfileImage());
            } else {
                throw new RuntimeException("User not found with ID: " + id);
            }
        }


        userProfileService.modify(userDto, id);


        request.getSession().setAttribute("user", userDto);

        return "redirect:/profile";
    } catch (Exception e) {
        System.err.println("Error updating profile: " + e.getMessage());
        return "account/edit_profile";
    }
}

   @GetMapping("delete_profile/{id}")
    public String deleteProfile(@PathVariable Long id) {
        userProfileService.delete(id);
        return "redirect:/log_in";
    }
}
