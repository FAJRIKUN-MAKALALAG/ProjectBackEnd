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

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebFilter("/*")  // Filter ini akan diterapkan pada semua URL
public class JwtRequestFilter implements Filter {

    @Autowired  // Spring akan secara otomatis menyuntikkan JwtUtil
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // Kecualikan /auth/login, /auth/login_admin dan /user/create
        if (requestURI.equals("/auth/login")
                || requestURI.equals("/auth/login_admin")
                || requestURI.equals("/user/create")
                || requestURI.equals("/product/create")
                || requestURI.equals("/product/list")
                || requestURI.startsWith("/product/update")   // Tambah ini
                || requestURI.startsWith("/product/delete")
                || requestURI.startsWith("/cart/user/{userId}")
                || requestURI.startsWith("/cart/add")){ // Tambah ini
            chain.doFilter(request, response);
            return;
        }



        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Ambil token setelah "Bearer "
            String username = jwtUtil.extractUsername(token);

            if (username != null && jwtUtil.validateToken(token, username)) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            }
        } else {
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
