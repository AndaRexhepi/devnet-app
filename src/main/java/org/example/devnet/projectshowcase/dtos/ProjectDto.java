package org.example.devnet.projectshowcase.dtos;

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
public class ProjectDto {

    @PositiveOrZero(message = "Id cannot be negative")
    private long id;


    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 100)
    private User username;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 100)
    private String description;

    @NotNull(message = "Image URL is required")
    @NotBlank(message = "Image URL is required")
    @Size(min = 2, max = 500)
    private String imageUrl;
}
