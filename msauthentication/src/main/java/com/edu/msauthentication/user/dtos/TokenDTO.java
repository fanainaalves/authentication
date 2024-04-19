package com.edu.msauthentication.user.dtos;

public record TokenDTO(String token) {

    public boolean isValidToken(String token){
        return token != null && !token.isEmpty();
    }
}
