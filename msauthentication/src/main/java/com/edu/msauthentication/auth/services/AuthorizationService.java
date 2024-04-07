package com.edu.msauthentication.auth.services;

import com.edu.msauthentication.security.TokenService;
import com.edu.msauthentication.user.dtos.AuthenticationDTO;
import com.edu.msauthentication.user.dtos.LoginResponseDTO;
import com.edu.msauthentication.user.dtos.RegisterDTO;
import com.edu.msauthentication.user.models.UserModel;
import com.edu.msauthentication.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        return null;
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data){
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.name(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    public ResponseEntity<Object> register (@RequestBody RegisterDTO registerDTO){
        if (this.userRepository.findByName(registerDTO.name()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserModel newUser = new UserModel(registerDTO.name(), encryptedPassword, registerDTO.role());
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
