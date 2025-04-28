package com.Project_backend.Controller;

import com.Project_backend.Entity.User;
import com.Project_backend.Repository.UserRepository;
import com.Project_backend.Util.JwtUtil;
import com.Project_backend.dto.LoginRequest;
import com.Project_backend.dto.LoginResponse;
import com.Project_backend.dto.MessageResponse;
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

    // Login untuk user biasa
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    String token = jwtUtil.generateToken(user);

                    // Balikin LoginResponse DTO supaya JSON rapi
                    return ResponseEntity.ok(new LoginResponse(token));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                            new MessageResponse("Password salah")
                    );
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("User tidak ditemukan")
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Terjadi kesalahan: " + e.getMessage())
            );
        }
    }

    // Login untuk admin
    @PostMapping("/login_admin")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Pastikan hanya admin yang bisa login ke endpoint ini
                if (user.getRole() != User.Role.ADMIN) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                            new MessageResponse("Hanya admin yang bisa login di sini")
                    );
                }

                if (user.getPassword().equals(loginRequest.getPassword())) {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok(new LoginResponse(token));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                            new MessageResponse("Password salah")
                    );
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new MessageResponse("User tidak ditemukan")
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Terjadi kesalahan: " + e.getMessage())
            );
        }
    }

    // Validasi Token
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestParam String token, @RequestParam String email) {
        try {
            boolean isValid = jwtUtil.validateToken(token, email);
            if (isValid) {
                return ResponseEntity.ok(new MessageResponse("Token valid"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new MessageResponse("Token tidak valid atau expired")
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new MessageResponse("Terjadi kesalahan saat memvalidasi token: " + e.getMessage())
            );
        }
    }
}
