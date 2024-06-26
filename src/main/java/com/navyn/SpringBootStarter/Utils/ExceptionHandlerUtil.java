package com.navyn.SpringBootStarter.Utils;

import com.navyn.SpringBootStarter.Exceptions.*;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.navyn.SpringBootStarter.Payload.Response;
import com.navyn.SpringBootStarter.Enums.ResponseType;

@RestControllerAdvice
public class ExceptionHandlerUtil {

    public static ResponseEntity<Response> handleException(Exception e) {
        if(e instanceof ResourceNotFoundException) {
            return ResponseEntity.status(404).body(new Response().setResponseType(ResponseType.RESOURCE_NOT_FOUND).setMessage("Resource Not found").setPayload(e.getMessage()));
        }else if(e instanceof BadCredentialsException) {
            return ResponseEntity.status(404).body(new Response().setResponseType(ResponseType.USER_NOT_FOUND).setMessage("The Requested user doesn't exist").setPayload(e.getMessage()));
        } else if (e instanceof DuplicateEmailException) {
            return ResponseEntity.status(400).body(new Response().setResponseType(ResponseType.USER_EXISTS).setMessage("The email is already in use by a different customer").setPayload(e.getMessage()));
        } else if (e instanceof AuthenticationFailedException) {
            return ResponseEntity.status(401).body(new Response().setResponseType(ResponseType.UNAUTHORIZED).setMessage("Unauthorized to perform the action").setPayload(e.getMessage()));
        } else if (e instanceof NotEnoughFundsException){
            return ResponseEntity.status(401).body(new Response().setResponseType(ResponseType.NOT_ENOUGH_FUNDS).setMessage("Insufficient funds").setPayload(e.getMessage()));
        }
        else if (e instanceof MalformedRequestException){
            return ResponseEntity.status(401).body(new Response().setResponseType(ResponseType.BAD_MALFORMED_REQUEST).setMessage("Malformed Request, Validate and Include all necessary properties").setPayload(e.getMessage()));
        }
        else {
            return ResponseEntity.status(500).body(new Response().setResponseType(ResponseType.INTERNAL_SERVER_ERROR).setPayload(e.getMessage()));
        }

    }
}
