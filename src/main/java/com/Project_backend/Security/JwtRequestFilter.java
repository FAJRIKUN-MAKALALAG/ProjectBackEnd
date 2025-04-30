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

@WebFilter("/*")
public class JwtRequestFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // Kecualikan URL yang tidak memerlukan token (sesuaikan URL sesuai kebutuhan)
        if (requestURI.equals("/auth/login")
                || requestURI.equals("/auth/login_admin")
                || requestURI.equals("/user/create")
                || requestURI.equals("/product/create")
                || requestURI.equals("/product/list")
                || requestURI.startsWith("/product/update")
                || requestURI.startsWith("/product/delete")
                || requestURI.startsWith("/cart/")
                || requestURI.startsWith("/order/") // Menangani URL /order/{userId} dan /order/detail/{id}
                || requestURI.startsWith("/payment/")
        ) {
            chain.doFilter(request, response);  // Jika tidak memerlukan token, lewati filter
            return;
        }

        // Ambil token dari header Authorization
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Ambil token setelah "Bearer "
            String username = jwtUtil.extractUsername(token);

            // Validasi token
            if (username != null && jwtUtil.validateToken(token, username)) {
                chain.doFilter(request, response);  // Token valid, lanjutkan filter
            } else {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            }
        } else {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Tidak ada konfigurasi khusus yang diperlukan
    }

    @Override
    public void destroy() {
        // Tidak ada resource yang perlu dihancurkan
    }
}

