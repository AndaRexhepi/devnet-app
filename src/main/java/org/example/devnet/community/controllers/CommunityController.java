package org.example.devnet.community.controllers;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.services.PostService;
import org.example.devnet.projectshowcase.helpers.FileHelperImpl;
import org.example.devnet.user.dtos.UserProfileDto;
import org.example.devnet.user.impls.UserProfileServiceImpl;
import org.example.devnet.user.mappers.UserProfileMapper;
import org.example.devnet.user.models.User;
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
    public final UserProfileMapper  userProfileMapper;

    @GetMapping("/community")
    public String findAll(Model model) {
        model.addAttribute("communities", communityService.findAll());
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("comment", new CommentDto());
        return "community/community";
    }

    @GetMapping("/view_community")
    public String viewCommunity(Model model) {
        model.addAttribute("communities", communityService.findAll());
        return "community/view_community";
    }

    @GetMapping("/create_community")
    public String createCommunity(Model model) {
        model.addAttribute("community", new CommunityDto());
        return "community/create_community";
    }

    @PostMapping("/community")
    public String createCommunity(@ModelAttribute CommunityDto communityDto,
                                  @RequestParam(value = "image", required = false) MultipartFile file, Model model
            , HttpServletRequest request) {
        model.addAttribute("community", communityDto);
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                        file.getOriginalFilename(),
                        file.getBytes());
                communityDto.setImageUrl("/assets/img/projects/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (request.getSession().getAttribute("user") != null) {

            Object sessionUser = request.getSession().getAttribute("user");

            if (sessionUser instanceof User) {
                UserProfileDto dto = userProfileMapper.toDto((User) sessionUser);
            } else if (sessionUser instanceof UserProfileDto) {
                User dto = userProfileMapper.toEntity((UserProfileDto) sessionUser);
                communityDto.setOwner(dto);
            } else {
                model.addAttribute("user", null);
            }
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








