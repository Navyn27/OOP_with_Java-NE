package com.navyn.SpringBootStarter.Auth;

import com.navyn.SpringBootStarter.Enums.TransactionType;
import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Repositories.CustomerRepository;
import com.navyn.SpringBootStarter.Services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final String makeAccountNumber(Integer length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        Set<String> generated = new HashSet<>();

        while (sb.length() < length) {
            sb.setLength(0); // Clear StringBuilder
            for (int i = 0; i < length; i++) {
                sb.append(random.nextInt(10));
            }
        }

        String randomString = sb.toString();
        while (generated.contains(randomString)) {
            randomString = makeAccountNumber(length);
        }
        generated.add(randomString);
        return randomString;
    }

    private final Double getInitialBalance(){
        return 0.0;
    }

    private final Date getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(date);
        return date;
    }

    public AuthenticationResponse register(RegisterRequest request){
        var customer = Customer.builder().balance(getInitialBalance()).dob(request.getDob()).lastUpdateDateTime(getCurrentDate()).account(makeAccountNumber(10)).firstname(request.getFirstname()).lastname(request.getLastname()).email(request.getEmail()).mobile(request.getMobile()).password(passwordEncoder.encode(request.getPassword())).build();
        repository.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
