package com.edu.authentication.auth.exception;

public class TokenValidationException extends RuntimeException{
    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
