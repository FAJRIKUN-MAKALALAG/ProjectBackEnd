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
                "/order/*", "/payment/*", // Menambahkan proteksi untuk order dan payment
                "/order/{userId}",  // Endpoint untuk membuat order
                "/order/detail/*",  // Endpoint untuk melihat detail order
                "/order/list", // Endpoint untuk melihat riwayat order
                "/payment/create/{orderId}",
                "/payment/checkout/{userId}",
                "/payment/{orderId}",  // Endpoint untuk melihat detail payment berdasarkan orderId
                "/payment/{orderId}/status", // Endpoint untuk memperbarui status payment
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
