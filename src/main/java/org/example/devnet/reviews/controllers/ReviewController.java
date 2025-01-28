package org.example.devnet.reviews.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.devnet.reviews.dtos.ReviewDto;
import org.example.devnet.reviews.services.ReviewService;
import org.example.devnet.user.dtos.UserProfileDto;
import org.example.devnet.user.dtos.UserRegistrationDto;
import org.example.devnet.user.mappers.UserProfileMapper;
import org.example.devnet.user.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    public final ReviewService reviewService;
    public final UserProfileMapper userProfileMapper;


    @GetMapping("/reviews")
    public String index(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return "reviews/reviews";
    }

    @GetMapping("new_review")
    public String newReview(Model model) {
        model.addAttribute("review", new ReviewDto());
        return "reviews/new_review";
    }

    @PostMapping("/reviews")
    public String addReview(Model model, @ModelAttribute ReviewDto reviewDto, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            UserProfileDto userDto = (UserProfileDto) request.getSession().getAttribute("user");
            User user = userProfileMapper.toEntity(userDto);
            reviewDto.setUsername(user);
            model.addAttribute("username", user.getUsername());
        }

        reviewService.add(reviewDto);
        return "redirect:/reviews";
    }

    @GetMapping("edit_review/{id}")
    public String editReview(Model model, @PathVariable Long id) {
        var reviewDto = reviewService.findById(id);
        model.addAttribute("review", reviewDto);
        return "reviews/edit_review";
    }

    @PostMapping("edit_review/{id}")
    public String modifyReview(@PathVariable Long id, @ModelAttribute ReviewDto reviewDto,  HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            UserProfileDto userDto = (UserProfileDto) request.getSession().getAttribute("user");
            User user = userProfileMapper.toEntity(userDto);
            reviewDto.setUsername(user);
        }
        reviewService.modify(reviewDto, id);
        return "redirect:/reviews";
    }

    @GetMapping("delete_review/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }


}
