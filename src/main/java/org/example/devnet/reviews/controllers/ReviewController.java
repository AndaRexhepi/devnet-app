package org.example.devnet.reviews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {

    @GetMapping("/reviews")
    public String index() {
        return "reviews/reviews";
    }
}
