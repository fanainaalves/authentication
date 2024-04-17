package com.edu.msauthentication.auth.controllers;

import com.edu.msauthentication.auth.services.AuthorizationService;
import com.edu.msauthentication.user.dtos.AuthenticationDTO;
import com.edu.msauthentication.user.dtos.RegisterDTO;
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

    @PostMapping("/api/v1/authorization/token/")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return  authorizationService.login(authenticationDTO);
    }

    @PostMapping("/api/v1/authorization/validation/")
    public ResponseEntity<Object> validateToken(@RequestBody RegisterDTO registerDTO){
        String token = registerDTO.getToken();
        if (registerDTO.isValidToken(token)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Token inválido!");
        }
    }
}
