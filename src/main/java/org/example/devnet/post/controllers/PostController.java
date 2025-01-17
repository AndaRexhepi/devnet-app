package org.example.devnet.post.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.models.Community;
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
    public final CommentService commentService;
    public final PostMapper postMapper;
    public final FileHelperImpl fileHelper;


    @GetMapping("/create_post")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        model.addAttribute("communities", communityService.findAll());
        return "post/create_post";
    }

    @PostMapping("/create_post")
    public String createPost(@ModelAttribute PostDto post, @RequestParam("image") MultipartFile file, Model model,
                             @RequestParam("communityName") String communityName) {
        model.addAttribute("post", post);
        try {
            String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/"
                    , file.getOriginalFilename()
                    , file.getBytes());
            post.setImageUrl("/assets/img/projects/" + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CommunityDto community = communityService.findByName(communityName);
        post.setCommunity(community);
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
    public String editPost(@ModelAttribute PostDto post, @PathVariable Long id, @RequestParam("image") MultipartFile file, Model model) {
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
        commentService.deleteByPostId(id);
        postService.delete(id);
        return "redirect:/community";
    }

    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable Long postId, @ModelAttribute CommentDto commentDto, Model model) {
        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("comment", new CommentDto());
        PostDto post = postService.findById(postId);
        commentDto.setPost(postMapper.toEntity(post));
        commentService.add(commentDto);
        return "redirect:/community";
    }


    @GetMapping("/post/{postId}/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
        commentService.delete(commentId);
        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("posts", postService.findAll());
        return "redirect:/community";
    }

//   @GetMapping("/post/{postId}/comment/edit/{commentId}")
//    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
//        model.addAttribute("post", postService.findById(postId));
//        model.addAttribute("comment", commentService.findById(commentId));
//        return "redirect:community";
//    }

    @PostMapping("/post/{postId}/comment/edit/{commentId}")
    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, @ModelAttribute CommentDto commentDto) {
        PostDto post = postService.findById(postId);
        commentDto.setPost(postMapper.toEntity(post));
        commentService.modify(commentDto, commentId);
        return "redirect:/community";
    }

    @GetMapping("/post/{postId}/comment/edit/{commentId}")
    public String showEditCommentPage(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("comment", commentService.findById(commentId)); // Load the specific comment
        return "redirect:/community";

    }
}




