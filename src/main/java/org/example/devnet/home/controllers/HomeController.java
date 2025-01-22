package org.example.devnet.home.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.devnet.user.dtos.UserProfileDto;

import org.example.devnet.user.mappers.UserProfileMapper;
import org.example.devnet.user.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

public final UserProfileMapper userProfileMapper;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        return "home/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        Object sessionUser = request.getSession().getAttribute("user");

        if (sessionUser instanceof User) {
            UserProfileDto dto = userProfileMapper.toDto((User) sessionUser);
            model.addAttribute("user", dto);
        } else if (sessionUser instanceof UserProfileDto) {
            model.addAttribute("user", sessionUser);
        } else {
            model.addAttribute("user", null);
        }

        return "home/home";
    }










}
