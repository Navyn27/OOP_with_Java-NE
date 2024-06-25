package com.navyn.SpringBootStarter.Auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "First name is mandatory")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    private String firstname;

    @NotNull(message = "Last name is mandatory")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastname;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be a valid email and unique")
    private String email;

    @NotNull(message = "Every customer must have a valid mobile number like +25078******* or +25073******* or *25079******* or +25072*******")
    @Pattern(
            regexp = "^\\+(2507[358]|25072)\\d{7}|\\*25079\\d{7}$\n",
            message = "A valid Rwandan registered phone number should be like:  +25078******* or +25073******* or *25079******* or +25072******* "
    )
    private String mobile;

    @NotNull(message = "Every customer must present a Date of Birth")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dob;

    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Password must contain at least one digit, one lowercase letter, and one uppercase letter"
    )
    private String password;

}
