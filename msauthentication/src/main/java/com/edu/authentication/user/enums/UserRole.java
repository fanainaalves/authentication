package com.edu.authentication.user.enums;

public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole (String role){
        this.role = role;
    }

    public static UserRole fromString(String roleString){
        for (UserRole role : UserRole.values()){
            if (role.role.equalsIgnoreCase(roleString)){
                return role;
            }
        }
        throw new IllegalArgumentException("Role do usuário não encontrado para o valor: " + roleString);
    }

}
