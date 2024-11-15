package com.hubertkuch.wehere.config;

import com.hubertkuch.wehere.account.AccountRepository;
import com.hubertkuch.wehere.exceptions.CannotAuthorizeException;
import com.hubertkuch.wehere.filters.AuthFiler;
import com.hubertkuch.wehere.utils.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AuthConfig {
    private final AccountRepository accountRepository;

    public AuthConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean(name = "whiteList")
    public String[] whiteList() {
        return new String[]{"/api/v1/auth/login", "/api/v1/auth/register", "/error", "/api/v1/account/genders"};
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return id -> accountRepository.findById(id)
                .orElseThrow(() -> new CannotAuthorizeException("User not found with id: " + id));
    }

    @Bean
    public AuthFiler authFiler(JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        return new AuthFiler(jwtUtils, userDetailsService, whiteList());
    }
}
