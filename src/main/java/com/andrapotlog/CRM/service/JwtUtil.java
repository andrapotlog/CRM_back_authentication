package com.andrapotlog.CRM.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(Long userId, String email) {
        /*String token = Jwts.builder()
                .setSubject(userData.getEmail())
                .claim("userId", userData.getId_user())
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .compact();*/
        System.out.println(secretKey);

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                /*.setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))*/
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
