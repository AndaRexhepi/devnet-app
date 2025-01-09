package org.example.devnet.post.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.mappers.PostMapper;
import org.example.devnet.post.models.Post;
import org.example.devnet.post.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;
    public final CommunityService communityService;



//    @GetMapping("/community")
//    public String findAll(Model model) {
//        model.addAttribute("posts", postService.findAll());
//        return "community/community";
//    }

    @GetMapping("/create_post")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("communities", communityService.findAll());
        return "post/create_post";
    }

    @PostMapping("/create_post")
    public String createPost(@ModelAttribute PostDto post) {
        postService.add(post);
        return "redirect:/community";
    }



}




