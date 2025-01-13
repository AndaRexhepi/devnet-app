package org.example.devnet.community.controllers;


import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.services.PostService;
import org.example.devnet.projectshowcase.helpers.FileHelperImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    public final CommunityService communityService;
    public final PostService postService;
    public final CommentService commentService;
    private final FileHelperImpl fileHelper;

    @GetMapping("/community")
    public String findAll(Model model) {
        model.addAttribute("communities", communityService.findAll());
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("comment", new CommentDto());
        return "community/community";
    }

    @GetMapping("/join_community")
    public String joinCommunity(Model model) {
        model.addAttribute("communities", communityService.findAll());
        return "community/join_community";
    }

    @GetMapping("/create_community")
    public String createCommunity(Model model) {
        model.addAttribute("community", new CommunityDto());
        return "community/create_community";
    }

    @PostMapping("/community")
    public String createCommunity(@ModelAttribute CommunityDto communityDto, @RequestParam("image") MultipartFile file, Model model) {
        model.addAttribute("community", communityDto);
        try {
            String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/"
                    , file.getOriginalFilename()
                    , file.getBytes());
            communityDto.setImageUrl("/assets/img/projects/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        communityService.add(communityDto);
        return "redirect:/community";
    }

    @GetMapping("edit_community/{id}")
    public String editCommunity(Model model, @PathVariable Long id) {
        model.addAttribute("community", communityService.findById(id));
        return "community/edit_community";
    }

    @PostMapping("edit_community/{id}")
    public String editCommunity(@ModelAttribute CommunityDto communityDto, @PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                        file.getOriginalFilename(),
                        file.getBytes());
                communityDto.setImageUrl("/assets/img/projects/" + fileName);
            } else {
                CommunityDto existingProject = communityService.findById(id);
                communityDto.setImageUrl(existingProject.getImageUrl());
            }
            communityService.modify(communityDto, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/community";
    }

    @GetMapping("/community_profile/{id}")
    public String communityProfile(Model model, @PathVariable Long id) {
        model.addAttribute("posts", postService.findByCommunityId(id));
        model.addAttribute("community", communityService.findById(id));
        return "community/community_profile";
    }

    @GetMapping("/community/{id}")
    public String deleteCommunity(@PathVariable Long id) {
        communityService.delete(id);
        return "redirect:/community";
    }


}








