package com.hubertkuch.wehere.account;

import com.hubertkuch.wehere.exceptions.ContentBusyException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record AccountService(AccountRepository accountRepository) {
    public boolean exists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    public Account createAccount(String username, String password) throws ContentBusyException {
        if (exists(username)) throw new ContentBusyException("User with that username exists");

        return accountRepository.save(new Account(UUID.randomUUID().toString(), username, password));
    }
}
