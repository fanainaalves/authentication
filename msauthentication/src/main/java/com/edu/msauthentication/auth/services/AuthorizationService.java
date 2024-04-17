package com.edu.msauthentication.auth.services;

import com.edu.msauthentication.security.TokenService;
import com.edu.msauthentication.user.dtos.AuthenticationDTO;
import com.edu.msauthentication.user.dtos.LoginResponseDTO;
import com.edu.msauthentication.user.dtos.RegisterDTO;
import com.edu.msauthentication.user.models.UserModel;
import com.edu.msauthentication.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import java.sql.Date;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        try{
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((UserModel) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário ou senha inválidos!");
        }
    }

    public ResponseEntity<Object> validateToken (@RequestBody RegisterDTO registerDTO){
            if (this.userRepository.findByUsername(registerDTO.username()) != null) {
                return ResponseEntity.badRequest().build();
            }
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
            UserModel newUser = new UserModel(registerDTO.username(), encryptedPassword, registerDTO.role());
            newUser.setCreatedAt(new Date(System.currentTimeMillis()));
            this.userRepository.save(newUser);
            return ResponseEntity.ok().build();
//        String token = registerDTO.getToken();
//
//        if(!registerDTO.isValidToken(token)){
//            return ResponseEntity.badRequest().body("Token Inválido");
//        }
//        String username = getUsernameFrom(token);
//        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
//
//        if (this.userRepository.findByName(username != null)){
//            return ResponseEntity.badRequest().body("Usuario já existe!");
//        }
//
//        UserModel newUser = new UserModel(username, encryptedPassword, registerDTO.getRole());
//        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
//        this.userRepository.save(newUser);
//
//        return ResponseEntity.ok().build();
    }

//    private static String getUsernameFrom(String token) {
//        return token;
//    }
}
