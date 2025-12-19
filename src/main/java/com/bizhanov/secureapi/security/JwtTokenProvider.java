package com.bizhanov.secureapi.security;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key signingKey;
    private final long expirationMs;

    @SuppressFBWarnings(value = "CT_CONSTRUCTOR_THROW", justification = "Exception is properly handled and re-thrown with context")
    public JwtTokenProvider(@Value("${jwt.secret}") String base64Secret,
                           @Value("${jwt.expiration-ms}") long expirationMs) {
        try {
            byte[] secretBytes = Base64.getDecoder().decode(base64Secret);
            this.signingKey = Keys.hmacShaKeyFor(secretBytes);
            this.expirationMs = expirationMs;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid JWT secret key format", e);
        }
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

