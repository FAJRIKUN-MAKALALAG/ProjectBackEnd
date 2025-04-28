package com.Project_backend.Service;

import com.Project_backend.Entity.User;
import com.Project_backend.Repository.UserRepository;
import com.Project_backend.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                // Token valid, return the JWT token
                return jwtUtil.generateToken(user);
            }
        }
        return null; // Invalid credentials
    }

    public boolean validateToken(String token, String email) {
        return jwtUtil.validateToken(token, email);
    }
}
