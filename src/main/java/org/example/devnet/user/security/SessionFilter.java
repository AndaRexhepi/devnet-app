package org.example.devnet.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Configuration
public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/style/fragments") ||
                requestURI.startsWith("/style/pages/account") ||
                requestURI.startsWith("/js") ||
                requestURI.startsWith("/images") ||
                requestURI.startsWith("/favicon.ico")){
            filterChain.doFilter(request, response);
            return;
        }


        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            if (request.getRequestURI().startsWith("/log_in") || request.getRequestURI().startsWith("/sign_up")) {
                filterChain.doFilter(request, response);
                return;
            }
            response.sendRedirect("/log_in");
            return;
        }


        if(request.getRequestURI().startsWith("/log_in") || request.getRequestURI().startsWith("/sign_up")) {
            response.sendRedirect("/");
            return;
        }
        filterChain.doFilter(request, response);


    }
}
