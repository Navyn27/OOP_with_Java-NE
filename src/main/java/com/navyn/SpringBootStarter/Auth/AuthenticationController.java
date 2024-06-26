package com.navyn.SpringBootStarter.Auth;

import com.navyn.SpringBootStarter.Enums.ResponseType;
import com.navyn.SpringBootStarter.Exceptions.DuplicateEmailException;
import com.navyn.SpringBootStarter.Utils.ExceptionHandlerUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.navyn.SpringBootStarter.Payload.Response;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Response> register(
            @Valid @RequestBody RegisterRequest request) {
        try{
            if(service.userExists(request)) throw new DuplicateEmailException("The Email is already in use by another customer");
            return ResponseEntity.status(200).body(new Response().setResponseType(ResponseType.SUCCESS).setMessage("Customer Registered Successfully").setPayload(service.register(request)));
        }
        catch(Exception e){
            return ExceptionHandlerUtil.handleException(e);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> register(
            @Valid @RequestBody AuthenticationRequest request) {
        try{
            return ResponseEntity.status(200).body(new Response().setResponseType(ResponseType.SUCCESS).setPayload(service.authenticate(request)));
        }
        catch(Exception e){
            return ExceptionHandlerUtil.handleException(e);
        }
    }
}