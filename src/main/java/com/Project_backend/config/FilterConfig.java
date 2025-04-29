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

        // Proteksi semua URL ini
        registrationBean.addUrlPatterns(
                "/order/*", "/payment/*",
                "/product/*",
                "/product/create",
                "/product/list",
                "/product/update/*", // Tambah update
                "/product/delete/*",
                "/cart/{userId}",
                "/cart/add"
        );

        return registrationBean;
    }
}
