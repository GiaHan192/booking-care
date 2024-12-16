package com.company.myweb.utils;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.common.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtilsHelper {
    @Value("${jwt.privateKey}")
    private String privateKey;

    @Value("${jwt.expiration}")
    private String expiration;


    public String generateToken(UserDTO users) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        long expirationNumber = Long.parseLong(expiration) * 1000;
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", users.getUserName());
        claims.put("fullName", users.getFullName());
        claims.put("role", users.getRoles().getRoleName());

        return Jwts.builder()
                .claims(claims)
                .subject(users.getUserName())
                .signWith(key)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationNumber))
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getAuthorizesFromToken(String token) {
        Claims claims = this.getAllClaimsFromToken(token).getPayload();
        return claims.get("role", String.class);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token).getPayload();
        return claimsResolver.apply(claims);
    }

    private Jws<Claims> getAllClaimsFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw ApiException.create(HttpStatus.UNAUTHORIZED)
                    .withMessage("JWT token invalid");
        }
    }
}
