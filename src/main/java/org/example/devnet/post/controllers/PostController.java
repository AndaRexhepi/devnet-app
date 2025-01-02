package org.example.devnet.post.controllers;

import org.example.devnet.post.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final List<Post> posts = new ArrayList<>();





    @GetMapping("/create_post")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "community/create_post";
    }

    @PostMapping("/create_post")
    public String createPost(@ModelAttribute Post post) {
        posts.add(post);
        return "redirect:/community";
    }

    @PostMapping("/community/{id}/delete")
    public String deletePost(@PathVariable int id) {
        posts.removeIf(post -> post.getId() == id);
        return "redirect:/community";
    }

}




