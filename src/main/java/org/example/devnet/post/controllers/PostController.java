package org.example.devnet.post.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.services.CommunityService;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.mappers.PostMapper;
import org.example.devnet.post.services.PostService;
import org.example.devnet.projectshowcase.helpers.FileHelperImpl;
import org.example.devnet.user.dtos.UserProfileDto;
import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.mappers.UserProfileMapper;
import org.example.devnet.user.mappers.UserRegistrationMapper;
import org.example.devnet.user.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PostController {

    public final PostService postService;
    public final CommunityService communityService;
    public final CommentService commentService;
    public final PostMapper postMapper;
    public final UserProfileMapper userProfileMapper;
    public final FileHelperImpl fileHelper;


    @GetMapping("/create_post")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        model.addAttribute("communities", communityService.findAll());
        return "post/create_post";
    }

    @PostMapping("/create_post")
    public String createPost(@ModelAttribute PostDto post,
                             @RequestParam("image") MultipartFile file,
                             @RequestParam("communityId") Long communityId, // Add this parameter
                             Model model,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        model.addAttribute("post", post);

        try {
            // Handle image upload
            if (file != null && !file.isEmpty()) {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/"
                        , file.getOriginalFilename()
                        , file.getBytes());
                post.setImageUrl("/assets/img/projects/" + fileName);
            }

            // Fetch the selected community by ID
            CommunityDto community = communityService.findById(communityId);
            if (community == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid community selected.");
                return "redirect:/create_post";
            }
            post.setCommunity(community); // Set the CommunityDto object

            // Set the user
            Object sessionUser = request.getSession().getAttribute("user");
            if (sessionUser != null) {
                User dto = userProfileMapper.toEntity((UserProfileDto) sessionUser);
                post.setUsername(dto);
            }

            // Save the post
            postService.add(post);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload image. Please try again.");
            return "redirect:/create_post";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/create_post";
        }

        return "redirect:/community";
    }

    @GetMapping("/edit_post/{id}")
    public String editPost(Model model, @PathVariable Long id) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("communities", communityService.findAll());
        return "post/edit_post";
    }

    //    String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/"
//            , file.getOriginalFilename()
//            , file.getBytes());
//            post.setImageUrl("/assets/img/projects/" + fileName);
//}
    @PostMapping("/edit_post/{id}")
    public String editPost(@ModelAttribute PostDto post,
                           @PathVariable Long id,
                           @RequestParam(value = "image", required = false) MultipartFile file,
                           Model model,
                           @RequestParam("communityId") Long communityId, // Use communityId instead of communityName
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {
        model.addAttribute("post", post);

        try {
            // Handle image upload
            if (file != null && !file.isEmpty()) {
                String fileName = fileHelper.uploadFile("target/classes/static/assets/img/projects/",
                        file.getOriginalFilename(),
                        file.getBytes());
                post.setImageUrl("/assets/img/projects/" + fileName);
            } else {
                // Retain the existing image URL if no new image is uploaded
                PostDto existingPost = postService.findById(id);
                post.setImageUrl(existingPost.getImageUrl());
            }

            // Fetch the selected community by ID
            CommunityDto community = communityService.findById(communityId);
            if (community == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid community selected.");
                return "redirect:/edit_post/" + id;
            }
            post.setCommunity(community);

            // Set the user
            Object sessionUser = request.getSession().getAttribute("user");
            if (sessionUser != null) {
                User dto = userProfileMapper.toEntity((UserProfileDto) sessionUser);
                post.setUsername(dto);
            }

            // Update the post
            postService.modify(post, id);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload image. Please try again.");
            return "redirect:/edit_post/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/edit_post/" + id;
        }

        return "redirect:/community";
    }

    @GetMapping("/delete_post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/community";
    }

//    @PostMapping("/post/{postId}/comment")
//    public String addComment(@PathVariable Long postId, @ModelAttribute CommentDto commentDto, Model model) {
//        model.addAttribute("post", postService.findById(postId));
//        model.addAttribute("comment", new CommentDto());
//        PostDto post = postService.findById(postId);
//        commentDto.setPost(postMapper.toEntity(post));
//        commentService.add(commentDto);
//        return "redirect:/community";
//    }
//
//
//    @GetMapping("/post/{postId}/comment/delete/{commentId}")
//    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
//        commentService.delete(commentId);
//        model.addAttribute("post", postService.findById(postId));
//        model.addAttribute("posts", postService.findAll());
//        return "redirect:/community";
//    }
//
////   @GetMapping("/post/{postId}/comment/edit/{commentId}")
////    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
////        model.addAttribute("post", postService.findById(postId));
////        model.addAttribute("comment", commentService.findById(commentId));
////        return "redirect:community";
////    }
//
//    @PostMapping("/post/{postId}/comment/edit/{commentId}")
//    public String editComment(@PathVariable Long postId, @PathVariable Long commentId, @ModelAttribute CommentDto commentDto) {
//        PostDto post = postService.findById(postId);
//        commentDto.setPost(postMapper.toEntity(post));
//        commentService.modify(commentDto, commentId);
//        return "redirect:/community";
//    }
//
//    @GetMapping("/post/{postId}/comment/edit/{commentId}")
//    public String showEditCommentPage(@PathVariable Long postId, @PathVariable Long commentId, Model model) {
//        model.addAttribute("post", postService.findById(postId));
//        model.addAttribute("comment", commentService.findById(commentId)); // Load the specific comment
//        return "redirect:/community";
//
//    }
}




