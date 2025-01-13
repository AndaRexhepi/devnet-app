package org.example.devnet.comment.controllers;

import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    public final CommentService commentService;


//    @PostMapping("/community")
//    public void addComment(Model model, @ModelAttribute CommentDto commentDto) {
//        commentService.add(commentDto);
//    }


    @GetMapping("/edit_comment/{id}")
    public void editComment(Model model, @PathVariable Long id) {
        model.addAttribute("comment", commentService.findById(id));
    }

    @GetMapping("/delete_comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }




}
