package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.account.Account;
import com.hubertkuch.wehere.account.AccountBody;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.exceptions.ContentBusyException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(AccountService accountService) {

    @PostMapping("/register")
    public Account createAccount(@RequestBody AccountBody body) throws ContentBusyException {

        return accountService.createAccount(body.username(), body.password());
    }

    @GetMapping("/test")
    public String hello() {
        return "test";
    }
}
