package com.edu.msauthentication.user.dtos;

public record RegisterDTO(String token, String username, String password, String role) {

    public boolean isValidToken(String token){
     return  this.token != null && !this.token.isEmpty();
 }

    public String getToken() {
        return token;
    }

    public CharSequence getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
