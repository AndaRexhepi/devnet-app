package org.example.devnet.community.dtos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {

    @PositiveOrZero(message = "Id cannot be negative")
    private long id;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
    private String description;

    @Size(min = 2, max = 100, message = "Image URL must be between 2 and 100 characters")
    private String imageUrl;

    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDate createdDate;




}
