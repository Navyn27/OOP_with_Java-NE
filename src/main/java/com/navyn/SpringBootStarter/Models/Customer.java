package com.navyn.SpringBootStarter.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),@UniqueConstraint(columnNames = "account")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
//public class UserData extends User ; when you want to use Spring Security's pre-built methods
//public class implements UserDetails when you want to customize methods
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotNull(message = "First name is mandatory")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    private String firstname;

    @NotNull(message = "Last name is mandatory")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastname;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be a valid email and unique")
    private String email;

    @NotNull(message = "Every customer must have a valid mobile number")
    @Pattern(
            regexp = "^\\+(2507[358]|25072)\\d{7}|\\*25079\\d{7}$\n",
            message = "A valid Rwandan registered phone number should be like:  +25078******* or +25073******* or *25079******* or +25072******* "
    )
    private String mobile;

    @NotNull(message = "Every customer must present a Date of Birth")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dob;

    @NotNull(message="Account value cannot be null")
    @Size(min = 5, max = 20, message = "Account must be between 1 and 20 characters")
    private String account;

    @NotNull(message = "Amount cannot be null")
    private Double balance;

    @NotNull(message = "Last update date and time cannot be null")
    @PastOrPresent(message = "Last update date and time cannot be in the future")
    private Date lastUpdateDateTime;

    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Password must contain at least one digit, one lowercase letter, and one uppercase letter"
    )
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<SavingsMgmtBanking> savings;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<WithdrawMgmtBanking> withdraws;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public String getPassword(){
        return password;
    }

}
