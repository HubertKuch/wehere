package com.hubertkuch.wehere.config;

import com.hubertkuch.wehere.account.AccountRepository;
import com.hubertkuch.wehere.filters.AuthFiler;
import com.hubertkuch.wehere.utils.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AuthConfig {
    private final AccountRepository accountRepository;

    public AuthConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
    }

    @Bean
    public AuthFiler authFiler(JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        return new AuthFiler(jwtUtils, userDetailsService);
    }
}
