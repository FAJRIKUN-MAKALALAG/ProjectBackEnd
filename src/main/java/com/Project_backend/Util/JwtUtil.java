package com.Project_backend.Util;

import com.Project_backend.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        try {
            // Menggunakan KeyGenerator untuk membuat secret key secara otomatis
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);  // Ukuran kunci dalam bit
            secretKey = keyGenerator.generateKey();
        } catch (Exception e) {
            System.out.println("Error generating secret key, using default key.");
            secretKey = Keys.hmacShaKeyFor("defaultsecretkey".getBytes());  // Fallback key jika gagal
        }
    }

    // Generate JWT Token
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())  // Menyimpan email dalam JWT
                .setIssuedAt(new Date())  // Waktu pembuatan token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Token berlaku 1 jam
                .signWith(secretKey, SignatureAlgorithm.HS256)  // Menandatangani token dengan secret key
                .compact();
    }

    // Extract username (email) from JWT token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // Mendapatkan email dari JWT
    }

    // Validate token against username and check if it's expired
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Cek apakah token sudah expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
