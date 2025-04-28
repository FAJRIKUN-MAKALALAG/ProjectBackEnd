package com.Project_backend.Util;

import com.Project_backend.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("mysecretkey")  // Kamu bisa menghapus ini, karena kita akan menggunakan Keys.secretKeyFor
    private String secretKey;

    // Generate JWT Token
    public String generateToken(User user) {
        // Gunakan Keys.secretKeyFor untuk menghasilkan kunci yang aman untuk HS256
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(user.getEmail()) // Menyimpan email sebagai subject dalam JWT
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Token berlaku 1 jam
                .signWith(key)  // Menggunakan kunci yang dihasilkan oleh Keys.secretKeyFor
                .compact();
    }

    // Extract username (email) dari token
    public String extractUsername(String token) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validasi token
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Cek apakah token sudah expired
    private boolean isTokenExpired(String token) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
