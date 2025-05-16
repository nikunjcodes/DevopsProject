package com.devops.userservice.dto;

import com.devops.userservice.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    @UniqueEmail
    private String email;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "provide valid phone number")
    private String phone;
}
