package com.hubertkuch.wehere.account;

import com.hubertkuch.wehere.exceptions.ContentBusyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.UUID;

@Service
public record AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    public boolean exists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    public Account createAccount(String username, String password, Gender gender, String avatar) throws ContentBusyException {
        if (exists(username)) throw new ContentBusyException("User with that username exists");

        Image image = ImageIO.re

        return Account.from(accountRepository.save(new AccountEntity(UUID.randomUUID().toString(), username, passwordEncoder.encode(password), gender)));
    }

    public Account getAccount(String username) {
        return accountRepository.findByUsername(username).map(Account::from).orElse(null);
    }

    public Account getAccountByHashtag(String hashtag) {
        return accountRepository.findByHashtag(hashtag).map(Account::from).orElse(null);
    }
}
