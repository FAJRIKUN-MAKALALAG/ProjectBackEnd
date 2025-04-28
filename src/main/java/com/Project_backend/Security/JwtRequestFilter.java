package com.Project_backend.Security;

import com.Project_backend.Util.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")  // Filter ini akan diterapkan pada semua URL
public class JwtRequestFilter implements Filter {

    private JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // Kecualikan /auth/login dan /user/create
        if (requestURI.equals("/auth/login") || requestURI.equals("/user/create")) {
            chain.doFilter(request, response);  // Lanjutkan ke filter berikutnya tanpa verifikasi token
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Ambil token setelah "Bearer "
            String username = jwtUtil.extractUsername(token);

            System.out.println("Token ditemukan: " + token);  // Log token untuk debug

            if (username != null && jwtUtil.validateToken(token, username)) {
                System.out.println("Token valid untuk user: " + username);  // Log jika token valid
                chain.doFilter(request, response);
            } else {
                System.out.println("Token tidak valid untuk user: " + username);  // Log jika token tidak valid
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            }
        } else {
            System.out.println("Authorization header tidak ditemukan");  // Log jika header tidak ada
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Tidak perlu implementasi jika tidak ada konfigurasi filter khusus
    }

    @Override
    public void destroy() {
        // Tidak perlu implementasi jika tidak ada resource yang perlu dihancurkan
    }
}
