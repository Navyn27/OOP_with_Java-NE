package com.navyn.SpringBootStarter.Exceptions;

public class ResourceNotFoundException extends  RuntimeException{
    public String property;
    public String value;
    public String entity;

    public String message;


    public ResourceNotFoundException(String entity, String value, String property){
        super(String.format("%s with %s %s not found", entity, property, value));
        this.entity = entity;
        this.value = value;
        this.property = property;
    }

    public ResourceNotFoundException(String entity, Long value, String property){
        super(String.format("%s with %s % not found", entity, property, value));
        this.entity = entity;
        this.value = String.valueOf(value);
        this.property = property;
    }

}
