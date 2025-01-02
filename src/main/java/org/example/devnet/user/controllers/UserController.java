package org.example.devnet.user.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("log_in")
    public String login(Model model){
        return "account/log_in";
    }

    @GetMapping("sign_up")
    public String signup(Model model){
        return "account/sign_up";
    }

    @GetMapping("profile")
    public String profile(Model model){
        return "account/profile";
    }
}
