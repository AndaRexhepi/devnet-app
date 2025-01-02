package org.example.devnet.projectshowcase.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    @GetMapping("/projectshowcase")
    public String project_showcase(Model model){
        return "projectshowcase/projectshowcase";
    }

}