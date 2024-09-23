package com.hubertkuch.wehere.auth;

import com.hubertkuch.wehere.account.Account;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    JWTUtils jwtUtils;
    @Mock
    AccountService accountService;
    @Mock
    PasswordEncoder passwordEncoder;
    AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        jwtUtils = new JWTUtils("secret");
        authService = new AuthService(jwtUtils, accountService, passwordEncoder);
    }

    @Test
    void shouldReturnTokenAfterValidLogin() {
        when(accountService.getAccount(anyString())).thenReturn(new Account("", "test", "test"));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        String token = authService.login("test", "test");

        assertThat(token).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenInvalidLogin() {
        when(accountService.getAccount(anyString())).thenReturn(null);

        assertThatThrownBy(() -> authService.login("test", "test"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidPassword() {
        when(accountService.getAccount(anyString())).thenReturn(new Account("", "", ""));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> authService.login("", ""));
    }
}