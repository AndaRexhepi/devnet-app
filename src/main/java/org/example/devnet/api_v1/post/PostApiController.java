package org.example.devnet.api_v1.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.comment.services.CommentService;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.mappers.PostMapper;
import org.example.devnet.post.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostApiController {
    public final PostService postService;
    public final PostMapper postMapper;
    public final CommentService commentService;

    @GetMapping
    public List<PostDto> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostDto findById(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping("/default")
    public PostDto getDefaultUser() {
        return new PostDto();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public PostDto add(@Valid @RequestBody PostDto postDto) {
        return postService.add(postDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public PostDto modify(@Valid @RequestBody PostDto postDto, @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return postService.modify(postDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        postService.delete(id);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostDto> incrementLikes(@PathVariable Long postId) {
        PostDto updatedPost = postService.incrementLikes(postId);
        return ResponseEntity.ok(updatedPost);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        PostDto post = postService.findById(postId);
        commentDto.setPost(postMapper.toEntity(post));
        CommentDto savedComment = commentService.add(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    // Delete a comment
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    // Update a comment
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        PostDto post = postService.findById(postId);
        commentDto.setPost(postMapper.toEntity(post));
        CommentDto updatedComment = commentService.modify(commentDto, commentId);
        return ResponseEntity.ok(updatedComment);
    }

    // Get a comment by ID (for editing)
    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
        CommentDto comment = commentService.findById(commentId);
        return ResponseEntity.ok(comment);
    }


}
