package com.edu.msauthentication.user.dtos;

import com.edu.msauthentication.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO (@NotNull String name,@NotNull String password, @NotNull UserRole role) {

}
