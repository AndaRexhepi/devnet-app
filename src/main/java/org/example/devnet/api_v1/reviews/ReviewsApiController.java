package org.example.devnet.api_v1.reviews;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;


import org.example.devnet.reviews.dtos.ReviewDto;
import org.example.devnet.reviews.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewsApiController {
    public final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ReviewDto findById(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return reviewService.findById(id);
    }

    @GetMapping("/default")
    public ReviewDto getDefaultUser() {
        return new ReviewDto();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ReviewDto add(@Valid @RequestBody ReviewDto ReviewDto) {
        return reviewService.add(ReviewDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ReviewDto modify(@Valid @RequestBody ReviewDto ReviewDto, @PathVariable Long id) {
        return reviewService.modify(ReviewDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        reviewService.delete(id);
    }



}
