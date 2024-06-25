package com.navyn.SpringBootStarter.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @ManyToOne
    @NotNull(message = "Every message has to have a dedicated customer")
    private Customer customer;

    @NotBlank(message = "Message content is mandatory")
    @Size(min = 2, max = 300, message = "Last name must be between 2 and 300 characters")
    private String message;

    @NotNull(message = "Messaging date cannot be null")
    @FutureOrPresent(message = "Message can be scheduled but the time of sending can't be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateTime;
}
