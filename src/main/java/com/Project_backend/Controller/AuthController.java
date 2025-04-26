package com.Project_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Menambahkan pengecekan panjang password
        if (loginRequest.getPassword().length() > 100) {
            throw new IllegalArgumentException("Password terlalu panjang");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Jika login berhasil, kamu bisa mengembalikan token atau status sukses
        return "Login sukses"; // Ganti dengan token jika menggunakan JWT atau lainnya
    }

    // DTO untuk menerima data login
    public static class LoginRequest {
        private String email;
        private String password;

        // Getter dan Setter
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
