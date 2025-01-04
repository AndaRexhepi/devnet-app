package org.example.devnet.projectshowcase.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.projectshowcase.dtos.ProjectDto;
import org.example.devnet.projectshowcase.helpers.FileHelperImpl;
import org.example.devnet.projectshowcase.models.Project;
import org.example.devnet.projectshowcase.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    public final ProjectService projectService;
    private final FileHelperImpl fileHelper;

    @GetMapping("/projectshowcase")
    public String project_showcase(Model model){
        model.addAttribute("projects", projectService.findAll());
        return "projectshowcase/projectshowcase";
    }

    @GetMapping("/create_project")
    public String create_project(Model model){
        model.addAttribute("project", new Project());
        return "projectshowcase/create_project";
    }

    @PostMapping("/projectshowcase")
    public String create_project(@ModelAttribute ProjectDto project, Model model, @RequestParam("image") MultipartFile file){
        model.addAttribute("project", project);
        try {
            String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/"
                    , file.getOriginalFilename()
                    , file.getBytes());
            project.setImageUrl("/assets/img/projects/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        projectService.add(project);
        return "redirect:/projectshowcase";
    }




}
