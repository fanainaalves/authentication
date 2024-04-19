package com.edu.msauthentication.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Object> home() {
        return ResponseEntity.ok().body("Bem-vindo à minha aplicação de autenticação!");
    }
}
