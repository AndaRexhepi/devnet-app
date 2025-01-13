package org.example.devnet.comment.dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.devnet.post.models.Post;
import org.example.devnet.user.models.User;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @PositiveOrZero(message = "Id cannot be negative")
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private User username;

    private Post post;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 500)
    private String comment;

    private LocalTime postedAt = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
}
