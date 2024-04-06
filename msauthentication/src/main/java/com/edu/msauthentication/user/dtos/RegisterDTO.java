package com.edu.msauthentication.user.dtos;

import cyber.login.jwt.system.loginsystemjwt.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public class RegisterDTO (@NotNull String email,@NotNull String password, @NotNull UserRole role ) {}
