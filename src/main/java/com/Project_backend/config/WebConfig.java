package com.Project_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Menentukan semua endpoint
                .allowedOrigins("http://localhost:5173") // Gantilah sesuai dengan alamat frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Mengizinkan HTTP method tertentu
                .allowedHeaders("*") // Mengizinkan semua header
                .allowCredentials(true); // Mengizinkan kredensial (cookies, authorization headers, etc)
    }
}
