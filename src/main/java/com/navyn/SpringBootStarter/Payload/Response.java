package com.navyn.SpringBootStarter.Payload;

import com.navyn.SpringBootStarter.Enums.ResponseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private ResponseType responseType;
    private String message;
    private Object payload;

    public Response setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response setPayload(Object payload) {
        this.payload = payload;
        return this;
    }
}

