package org.example.devnet.user.dtos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @PositiveOrZero(message = "Id cannot be negative")
    private long id;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Surname is required")
    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters")
    private String username;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters")
    private String email;

    @Size(min = 2, max = 100, message = "Profile image must be between 2 and 100 characters")
    private String profileImage;

    @Size(min = 2, max = 500, message = "Bio must be between 2 and 500 characters")
    private String bio;

    @Size(min = 2, max = 100, message = "Password must be between 2 and 100 characters")
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String confirmPassword;


}

