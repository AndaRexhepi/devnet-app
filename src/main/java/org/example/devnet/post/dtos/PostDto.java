package org.example.devnet.post.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.devnet.comment.dtos.CommentDto;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.user.models.User;
import org.example.devnet.community.models.Community;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    @PositiveOrZero(message = "Id cannot be negative")
    private long id;

    @NotNull(message = "User is required")
    private User username;

    @NotNull(message = "Community is required")
    private CommunityDto community;

    private Long communityId;

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotNull(message = "Body of the post is required")
    @NotBlank(message = "Body of the post is required")
    @Size(min = 2, max = 1000, message = "Body must be between 2 and 500 characters")
    private String body;

    @Size(max = 1000, message = "Image URL must be less than 1000 characters")
    private String imageUrl;

    private List<CommentDto> comments;

    @PastOrPresent(message = "Posted time cannot be in the future")
    private LocalTime postedAt = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());


}