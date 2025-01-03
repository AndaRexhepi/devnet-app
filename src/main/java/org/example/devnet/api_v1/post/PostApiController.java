package org.example.devnet.api_v1.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.devnet.post.dtos.PostDto;
import org.example.devnet.post.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostApiController {
    public final PostService postService;

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

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public PostDto modify(@Valid @RequestBody PostDto postDto) {
        return postService.modify(postDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        postService.delete(id);
    }



}
