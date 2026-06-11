//package org.example.project.service.Jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.example.project.entity.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Service
//public class JwtService {
//    @Value("${spring.jwt.secret-key}")
//    private String secretKey;
//    private SecretKey key;
//    @PostConstruct
//    public void init(){
//        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
////    private final long ACCESS_EXPIRE =
////            1000 * 60 * 15;
////
////    private final long REFRESH_EXPIRE =
////            1000L * 60 * 60 * 24 * 7;
//
//    private final long expiration = 900000;
//    private final long refreshExpiration = 86400000;
//
//    public String generateToken(User user) {
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .claim("role",user.getRole().name())
//                .setIssuedAt(new Date())
//                .setExpiration(
//                        new Date(
//                                System.currentTimeMillis()
//                                        + expiration
//                        )
//                )
//                .signWith(key)
//                .compact();
//    }
//    public String generateRefreshToken(User user){
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(
//                        new Date(
//                                System.currentTimeMillis()
//                                        + refreshExpiration
//                        )
//                )
//                .signWith(key)
//                .compact();
//    }
//    public String extractUsername(String token){
//
//        return extractAllClaims(token)
//                .getSubject();
//    }
//
//    public Date extractExpiration(String token){
//
//        return extractAllClaims(token)
//                .getExpiration();
//    }
//
//    public Claims extractAllClaims(String token){
//
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isValid(String token){
//
//        try{
//
//            extractAllClaims(token);
//
//            return true;
//
//        }catch (Exception ex){
//
//            return false;
//        }
//    }
//
//}

package org.example.project.service.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.project.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret-key}")
    private String secretKey;
    private SecretKey key;
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    private final long expiration = 900000;
    private final long refreshExpiration = 86400000;
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration
                        )
                )
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(key)
                .compact();
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}