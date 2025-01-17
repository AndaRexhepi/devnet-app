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

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @PositiveOrZero(message = "Id cannot be negative")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank(message = "Username is required")
    @NotNull(message = "Username is required")
    @Size(min = 2, max = 100)
    private User username;

    @ManyToOne
    @JoinColumn(name = "community_id")
    @NotBlank(message = "Community is required")
    @NotNull(message = "Community is required")
    @Size(min = 2, max = 100)
    private CommunityDto community;

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100)
    private String title;

    @NotNull(message = "Body of the post is required")
    @NotBlank(message = "Body of the post is required")
    @Size(min = 2, max = 500)
    private String body;

    @Size(min = 2, max = 1000)
    private String imageUrl;

    @PositiveOrZero(message = "Likes cannot be negative")
    private int likes;

    @Size(min = 2, max = 500)
    private List<CommentDto> comments;

    @PastOrPresent(message = "Posted time cannot be in the future")
    private LocalTime postedAt = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());

}
