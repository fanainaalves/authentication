package com.edu.authentication.user.dtos;

public record TokenDTO(String token) {

    public boolean isValidToken(String token){
        return token != null && !token.isEmpty();
    }
}
