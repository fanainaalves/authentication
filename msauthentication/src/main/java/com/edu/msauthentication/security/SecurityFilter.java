package com.edu.msauthentication.security;

import java.io.IOException;

import com.edu.msauthentication.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoveryToken(request);
    if (token != null){
        var name = tokenService.validateToken(token);
        UserDetails user = userRepository.findByName(name);

        if (user != null) {
            var authtentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication
                    (authtentication);
        }
    }
    filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null){
            return null;
        } else {
        return authHeader.replace("Bearer ", "");
        }
    }
}
