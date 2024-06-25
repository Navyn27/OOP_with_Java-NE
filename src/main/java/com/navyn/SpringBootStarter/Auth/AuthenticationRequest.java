package com.navyn.SpringBootStarter.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be a valid email")
    private String email;

    @NotNull(message = "Email is mandatory")
    private String password;
}
