package org.example.devnet.reviews.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.reviews.dtos.ReviewDto;
import org.example.devnet.reviews.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    public final ReviewService reviewService;


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
    public String addReview(Model model, @ModelAttribute ReviewDto reviewDto) {
        model.addAttribute("review", reviewDto);
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
    public String modifyReview(@PathVariable Long id, @ModelAttribute ReviewDto reviewDto) {
        reviewService.modify(reviewDto, id);
        return "redirect:/reviews";
    }

    @GetMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return "redirect:/reviews";
    }



}
