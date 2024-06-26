package com.navyn.SpringBootStarter.Models;

import com.navyn.SpringBootStarter.Enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name="withdraw_mgmt_banking")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawMgmtBanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Customer cannot be null")
    private Customer customer;

    @NotBlank(message = "Account cannot be blank")
    @Size(min = 1, max = 20, message = "Account must be between 1 and 20 characters")
    private String account;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType type;

    @NotNull(message = "Banking date and time cannot be null")
    @PastOrPresent(message = "Banking date and time cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date bankingDateTime;
}
