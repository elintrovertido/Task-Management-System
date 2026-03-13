//package com.tms.authenticationservice.service;
//
//import com.tms.authenticationservice.model.CustomUserDetails;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.security.KeyStore;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class JwtService {
//
//    private final String SECRET_KEY = "task-management-system";
//
//    public String generateToken(CustomUserDetails user) {
//
//        Map<String, Object> claims = new HashMap<>();
//
//        claims.put("username", user.getUsername());
//        claims.put("email", user.getEmail());
//        claims.put("role", user.getRole());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public Claims extractClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String getUserName(String token) {
//        Claims claims = extractClaims(token);
//        return claims.get("userName", String.class);
//    }
//
//    public String getEmail(String token){
//        Claims claims = extractClaims(token);
//        return claims.get("email", String.class);
//    }
//
//    public String getRole(String token){
//        Claims claims = extractClaims(token);
//        return claims.get("role", String.class);
//    }
//
//    public Date getExpiration(String token) {
//        Claims claims = extractClaims(token);
//        return claims.getExpiration();
//    }
//
//}