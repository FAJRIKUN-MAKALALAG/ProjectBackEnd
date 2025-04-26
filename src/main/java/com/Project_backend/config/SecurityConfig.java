package com.Project_backend. config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Menonaktifkan CSRF (hati-hati dalam penerapan)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/login").permitAll()  // Sesuaikan endpoint yang dibolehkan akses
                        .anyRequest().authenticated()  // Semua request lainnya memerlukan otentikasi
                )
                .formLogin().permitAll() // Menggunakan form login
                .and()
                .httpBasic(); // Untuk login dasar menggunakan HTTP Basic (Jika diperlukan)

        return http.build();  // Menyelesaikan konfigurasi
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Menggunakan BCrypt untuk enkripsi password
    }
}
