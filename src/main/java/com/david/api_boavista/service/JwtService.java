package com.david.api_boavista.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.david.api_boavista.entities.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtService(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration-time}") long expiration) {

            byte[] keyBytes = Decoders.BASE64.decode(secret);

            this.secretKey = Keys.hmacShaKeyFor(keyBytes);
            this.expirationTime = expiration;
    }

    public String gerarToken(Usuario usuario) {

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expirationTime);

        return Jwts.builder()
            .subject(usuario.getUsername())
            .claim("role", usuario.getRole().getAuthority())
            .issuedAt(agora)
            .expiration(expiracao)
            .signWith(secretKey)
            .compact();
    }

    public String extrairUsername(String token) {
        
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
