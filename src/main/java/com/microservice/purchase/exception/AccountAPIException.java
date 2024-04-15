package com.microservice.purchase.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AccountAPIException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    private final String message;

    public AccountAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
