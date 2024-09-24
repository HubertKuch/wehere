package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.account.Account;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.auth.AuthService;
import com.hubertkuch.wehere.exceptions.ContentBusyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(AccountService accountService, AuthService authService) {

    @PostMapping("/register")
    public AccountResponse createAccount(@RequestBody AccountBody body) throws ContentBusyException {
        return AccountResponse.from(accountService.createAccount(body.username, body.password));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginBody body) {
        return new LoginResponse(authService.login(body.username(), body.password()));
    }

    public record AccountResponse(String id, String username) {
        public static AccountResponse from(Account account) {
            return new AccountResponse(account.id(), account.username());
        }
    }

    public record LoginResponse(String token) {
    }

    public record LoginBody(String username, String password) {
    }

    public record AccountBody(String username, String password) {
    }
}
