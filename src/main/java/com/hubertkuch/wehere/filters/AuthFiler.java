package com.hubertkuch.wehere.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hubertkuch.wehere.account.AccountDetailsService;
import com.hubertkuch.wehere.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFiler extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final AccountDetailsService accountDetailsService;

    public AuthFiler(JWTUtils jwtUtils, AccountDetailsService accountDetailsService) {
        this.jwtUtils = jwtUtils;
        this.accountDetailsService = accountDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT bearer token");
            return;
        }

        String token = header.replace("Bearer ", "").trim();
        String userId = jwtUtils.verifyToken(token);
        UserDetails userDetails = accountDetailsService.loadUserByUsername(userId);
        var authToken =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        if(SecurityContextHolder.getContext().getAuthentication() == null){
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
