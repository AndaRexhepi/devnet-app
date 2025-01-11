package org.example.devnet.projectshowcase.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.projectshowcase.dtos.ProjectDto;
import org.example.devnet.projectshowcase.helpers.FileHelperImpl;
import org.example.devnet.projectshowcase.models.Project;
import org.example.devnet.projectshowcase.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/edit_project/{id}")
    public String edit_project(Model model, @PathVariable Long id){
        model.addAttribute("project", projectService.findById(id));
        return "projectshowcase/edit_project";
    }

    @PostMapping("/edit_project/{id}")
    public String edit_project(@PathVariable Long id, @ModelAttribute ProjectDto project,  @RequestParam("image") MultipartFile file){
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                        file.getOriginalFilename(),
                        file.getBytes());
                project.setImageUrl("/assets/img/projects/" + fileName);
            } else {
                ProjectDto existingProject = projectService.findById(id);
                project.setImageUrl(existingProject.getImageUrl());
            }
            projectService.modify(project, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/projectshowcase";
    }

    @GetMapping("/delete_project/{id}")
    public String delete_project(@PathVariable Long id){
        projectService.delete(id);
        return "redirect:/projectshowcase";
    }






}
