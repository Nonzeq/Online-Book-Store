package com.kobylchak.bookstore.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${jwt.expiration}")
    private long expiration;
    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretStr) {
        this.secretKey = Keys.hmacShaKeyFor(secretStr.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userEmail) {
        return Jwts.builder()
                   .setSubject(userEmail)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                   .signWith(secretKey)
                   .compact();

    }

    public boolean isValidToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                                    .setSigningKey(secretKey)
                                    .build()
                                    .parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }
}
