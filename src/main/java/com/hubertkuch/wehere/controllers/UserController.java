package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.account.Account;
import com.hubertkuch.wehere.account.AccountBody;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.exceptions.ContentBusyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
public record UserController(AccountService accountService) {

    @PostMapping("")
    public Account createAccount(@RequestBody AccountBody body) throws ContentBusyException {
        return accountService.createAccount(body.username(), body.password());
    }


}
