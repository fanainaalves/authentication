package com.edu.msauthentication.auth.controllers;

import com.edu.msauthentication.auth.services.AuthorizationService;
import com.edu.msauthentication.user.dtos.AuthenticationDTO;
import com.edu.msauthentication.user.dtos.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/api/vi/authentication/token/")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        return  authorizationService.login(authenticationDTO);
    }

    @PostMapping("/api/vi/authentication/validation/")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO){
        return authorizationService.register(registerDTO);
    }
}
