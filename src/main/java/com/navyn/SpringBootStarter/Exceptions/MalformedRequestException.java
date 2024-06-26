package com.navyn.SpringBootStarter.Exceptions;

public class MalformedRequestException extends RuntimeException{
    public MalformedRequestException(String message) {
        super(message);
    }
}
