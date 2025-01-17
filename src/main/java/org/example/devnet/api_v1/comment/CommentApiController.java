package org.example.devnet.api_v1.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentApiController {

    public final CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/default")
    public CommentDto getDefaultComment() {
        return new CommentDto();
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById( @PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public CommentDto addComment(@Valid @RequestBody CommentDto commentDto) {
        return commentService.add(commentDto);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public CommentDto updateComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        return commentService.modify(commentDto, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }




}
