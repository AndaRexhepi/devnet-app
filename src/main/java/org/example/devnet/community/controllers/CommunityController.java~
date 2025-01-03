package org.example.devnet.community.controllers;

import org.example.devnet.post.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;

@Controller
public class CommunityController {

    private final List<Post> posts = new ArrayList<>();

//    public CommunityController() {
//        posts.add(new Post(1, new User(), new Community(), "First Post", "This is the content of the first post.", 0, 0, LocalTime.now()));
//        posts.add(new Post(2, new User(), new Community(), "Second Post", "This is the content of the first post.", 0, 0, LocalTime.now()));
//        posts.add(new Post(2, new User(), new Community(), "Third Post", "This is the content of the first post.", 0, 0, LocalTime.now()));
//    }

    @GetMapping("/community")
    public String community(Model model) {
        model.addAttribute("posts", posts);
        return "community/community";
    }

    @GetMapping("/join_community")
    public String joinCommunity(Model model) {
        return "community/join_community";
    }

    @GetMapping("/community_profile")
    public String communityProfile(Model model) {
        model.addAttribute("posts", posts);
        return "community/community_profile";
    }


    }








