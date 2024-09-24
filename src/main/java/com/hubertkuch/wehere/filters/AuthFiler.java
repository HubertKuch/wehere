package com.hubertkuch.wehere.filters;

import com.hubertkuch.wehere.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFiler extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final UserDetailsService accountDetailsService;

    public AuthFiler(JWTUtils jwtUtils, UserDetailsService accountDetailsService) {
        this.jwtUtils = jwtUtils;
        this.accountDetailsService = accountDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        if (request.getServletPath().toLowerCase().contains("/api/v1/auth/login") || request.getServletPath()
                .toLowerCase()
                .contains("/api/v1/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT bearer token");
            return;
        }

        String token = header.replace("Bearer ", "").trim();
        String userId = jwtUtils.verifyToken(token);

        System.out.println(userId);

        UserDetails userDetails = accountDetailsService.loadUserByUsername(userId);
        var authToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
