package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.account.Account;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.account.Gender;
import com.hubertkuch.wehere.auth.AuthService;
import com.hubertkuch.wehere.exceptions.ContentBusyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(AccountService accountService, AuthService authService) {

    @PostMapping("/register")
    public AccountResponse createAccount(@RequestBody AccountBody body) throws ContentBusyException {
        return AccountResponse.from(accountService.createAccount(body.username, body.password, body.gender));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginBody body) {
        return new LoginResponse(authService.login(body.username(), body.password()));
    }

    @GetMapping("/account")
    @PreAuthorize("isAuthenticated()")
    public AccountResponse getAccount(Authentication principal) {
        return AccountResponse.from(accountService.getAccount(String.valueOf(principal.getPrincipal())));
    }

    public record AccountResponse(String id, String username, Gender gender) {
        public static AccountResponse from(Account account) {
            return new AccountResponse(account.getId(), account.getUsername(), account.getGender());
        }
    }

    public record LoginResponse(String token) {
    }

    public record LoginBody(String username, String password) {
    }

    public record AccountBody(String username, String password, Gender gender) {
    }
}
