package com.edu.authentication.auth.controllers;

import com.edu.authentication.auth.exception.TokenValidationException;
import com.edu.authentication.auth.services.AuthorizationService;
import com.edu.authentication.auth.error.ErrorResponse;
import com.edu.authentication.security.TokenService;
import com.edu.authentication.user.dtos.AuthenticationDTO;
import com.edu.authentication.user.dtos.RegisterDTO;
import com.edu.authentication.user.dtos.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/api/v1/authorization")
public class AuthController {

    private final AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public AuthController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterDTO registerDTO){
        return authorizationService.registerUser(registerDTO);
    }

    @PostMapping("/token")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return  authorizationService.login(authenticationDTO);
    }

    @PostMapping("/validation")
    public ResponseEntity<Object> validateToken(@RequestBody @Valid TokenDTO tokenDTO){
        try{
            tokenService.validateToken(tokenDTO.token());
            return ResponseEntity.ok().build();
        }catch (TokenValidationException e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<Object> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return ResponseEntity.ok(userDetails);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
    }
}
