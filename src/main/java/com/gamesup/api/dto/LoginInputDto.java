package com.gamesup.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInputDto {
    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    private String password;
}
