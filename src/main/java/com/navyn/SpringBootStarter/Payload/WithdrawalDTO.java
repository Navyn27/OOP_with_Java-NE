package com.navyn.SpringBootStarter.Payload;

import com.navyn.SpringBootStarter.Enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalDTO {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @NotNull(message = "Valid signed token is required")
    private String token;
}
