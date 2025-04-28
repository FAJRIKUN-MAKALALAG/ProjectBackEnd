package com.Project_backend.Controller;

import com.Project_backend.Entity.User;
import com.Project_backend.Repository.UserRepository;
import com.Project_backend.Util.JwtUtil;
import com.Project_backend.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // Constructor Injection
    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok("Bearer " + token);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password salah");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan: " + e.getMessage());
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestParam String token, @RequestParam String email) {
        try {
            // Validasi token untuk memastikan token yang diberikan valid dan belum expired
            boolean isValid = jwtUtil.validateToken(token, email);

            if (isValid) {
                return ResponseEntity.ok("Token valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token tidak valid atau expired");
            }
        } catch (Exception e) {
            // Tangani error yang mungkin terjadi, misalnya token rusak atau kesalahan lainnya
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan saat memvalidasi token: " + e.getMessage());
        }
    }

}
