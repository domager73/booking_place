package org.example.booking_place.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_place.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = ".+@edu\\.centraluniversity\\.ru$",
            message = "Email must be from centraluniversity domain")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Full name cannot be blank")
    @Pattern(regexp = "^[A-Z][a-z]+( [A-Z][a-z]+){1,2}$",
            message = "Full name must be in format 'Name Surname Patronymic'")
    private String fullName;

    public User toUserModel(){
        User user  = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);

        return user;
    }
}