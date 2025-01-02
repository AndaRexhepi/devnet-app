package org.example.devnet.reviews.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.devnet.user.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    @PositiveOrZero(message = "Id cannot be negative")
    private long id;

    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 100)
    private User username;

    @NotBlank(message = "Role is required")
    @NotNull(message = "Role is required")
    @Size(min = 2, max = 100)
    private String role;

    private int rating;

    @NotNull(message = "Review is required")
    @NotBlank(message = "Review is required")
    @Size(min = 2, max = 500)
    private String review;
}
