package com.Project_backend.config;

import com.Project_backend.Security.JwtRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtFilter() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtRequestFilter());

        // Tentukan URL yang harus diproteksi dengan JWT
        registrationBean.addUrlPatterns("/order/*", "/payment/*", "/cart/*"); // Proteksi URL yang diinginkan

        // Kecualikan URL yang tidak perlu melalui filter
        registrationBean.addUrlPatterns("/user/create", "/auth/login");  // Kecualikan login dan pembuatan user

        return registrationBean;
    }
}
