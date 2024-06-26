package com.navyn.SpringBootStarter.Auth;

import com.navyn.SpringBootStarter.Models.Customer;
import com.navyn.SpringBootStarter.Repositories.CustomerRepository;
import com.navyn.SpringBootStarter.ServiceImpls.CustomerServiceImpl;
import com.navyn.SpringBootStarter.Services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    private CustomerServiceImpl customerService;


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

    try {
        var customer = Customer.builder().balance(getInitialBalance()).dob(request.getDob()).lastUpdateDateTime(getCurrentDate()).account(makeAccountNumber(10)).firstname(request.getFirstname()).lastname(request.getLastname()).email(request.getEmail()).mobile(request.getMobile()).password(passwordEncoder.encode(request.getPassword())).build();
        repository.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    catch(Exception e){
        e.printStackTrace();
        ResponseEntity.badRequest().build();
        return null;
    }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        var user = repository.findByEmail(request.getEmail()).orElseThrow(()->new BadCredentialsException("The requested user doesn't exist")) ;
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public Boolean userExists(RegisterRequest request){
        return repository.findByEmail(request.getEmail()).isPresent();
    }
}
