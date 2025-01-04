package org.example.devnet.community.controllers;

import org.example.devnet.post.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;

@Controller
public class CommunityController {


    @GetMapping("/community")
    public String community(Model model) {

        return "community/community";
    }

    @GetMapping("/join_community")
    public String joinCommunity(Model model) {
        return "community/join_community";
    }

    @GetMapping("/community_profile")
    public String communityProfile(Model model) {

        return "community/community_profile";
    }

    


}








