package com.edu.msauthentication.auth.controllers;

import com.edu.msauthentication.auth.services.AuthorizationService;
import com.edu.msauthentication.auth.error.ErrorResponse;
import com.edu.msauthentication.user.dtos.AuthenticationDTO;
import com.edu.msauthentication.user.dtos.RegisterDTO;
import com.edu.msauthentication.user.dtos.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthorizationService authorizationService;

    @Autowired
    public AuthController(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    @PostMapping("/api/v1/authorization/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterDTO registerDTO){
        return authorizationService.registerUser(registerDTO);
    }

    @PostMapping("/api/v1/authorization/token")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return  authorizationService.login(authenticationDTO);
    }

    @PostMapping("/api/v1/authorization/validation")
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
