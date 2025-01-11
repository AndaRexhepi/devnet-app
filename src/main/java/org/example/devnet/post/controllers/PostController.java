package org.example.devnet.post.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.mappers.PostMapper;
import org.example.devnet.post.models.Post;
import org.example.devnet.post.services.PostService;
import org.example.devnet.projectshowcase.dtos.ProjectDto;
import org.example.devnet.projectshowcase.helpers.FileHelperImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;
    public final CommunityService communityService;
    public final FileHelperImpl fileHelper;


    @GetMapping("/create_post")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        model.addAttribute("communities", communityService.findAll());
        return "post/create_post";
    }

    @PostMapping("/create_post")
    public String createPost(@ModelAttribute PostDto post, @RequestParam("image")MultipartFile file, Model model) {
        model.addAttribute("post", post);
        try {
            String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/"
                    , file.getOriginalFilename()
                    , file.getBytes());
            post.setImageUrl("/assets/img/projects/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        postService.add(post);
        return "redirect:/community";
    }

    @GetMapping("/edit_post/{id}")
    public String editPost(Model model, @PathVariable Long id) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("communities", communityService.findAll());
        return "post/edit_post";
    }

    @PostMapping("/edit_post/{id}")
    public String editPost(@ModelAttribute PostDto post, @PathVariable Long id, @RequestParam("image")MultipartFile file, Model model) {
        model.addAttribute("post", post);
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                        file.getOriginalFilename(),
                        file.getBytes());
                post.setImageUrl("/assets/img/projects/" + fileName);
            } else {
                PostDto existingProject = postService.findById(id);
                post.setImageUrl(existingProject.getImageUrl());
            }
            postService.modify(post, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        postService.modify(post, id);
        return "redirect:/community";
    }

    @GetMapping("/delete_post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/community";
    }

}




