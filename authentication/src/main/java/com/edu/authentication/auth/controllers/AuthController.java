package com.edu.authentication.auth.controllers;

import com.edu.authentication.auth.services.AuthorizationService;
import com.edu.authentication.auth.error.ErrorResponse;
import com.edu.authentication.user.dtos.AuthenticationDTO;
import com.edu.authentication.user.dtos.RegisterDTO;
import com.edu.authentication.user.dtos.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/api/v1/authorization")
public class AuthController {

    private final AuthorizationService authorizationService;

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
    public ResponseEntity<Object> validateToken(@RequestBody TokenDTO tokenDTO){
        if (tokenDTO.isValidToken(tokenDTO.token())){
            System.out.println("Token Válido");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Token inválido");
            return ResponseEntity.badRequest().body(new ErrorResponse("Token inválido!"));
        }
    }
}
