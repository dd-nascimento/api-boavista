package com.david.api_boavista.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.api_boavista.dto.LoginRequestDTO;
import com.david.api_boavista.dto.LoginResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.service.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
        AuthenticationManager authenticationManager, 
        JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
        @Valid @RequestBody LoginRequestDTO loginRequest) {
            
            UsernamePasswordAuthenticationToken credentials = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), 
                    loginRequest.getPassword()
                );
            Authentication authentication =
                authenticationManager.authenticate(credentials);
            
            Usuario usuario = (Usuario) authentication.getPrincipal();
            
            String token = jwtService.gerarToken(usuario);
            
            return new LoginResponseDTO(token);
    }

}
