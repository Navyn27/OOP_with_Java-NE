package com.navyn.SpringBootStarter.Exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateEmailException extends RuntimeException {
    private String message;
    public DuplicateEmailException(String message) {
    }
}
