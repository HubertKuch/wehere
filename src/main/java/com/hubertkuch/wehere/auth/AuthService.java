package com.hubertkuch.wehere.auth;

import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.exceptions.CannotAuthorizeException;
import com.hubertkuch.wehere.utils.JWTUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record AuthService(JWTUtils jwtUtils, AccountService accountService, PasswordEncoder passwordEncoder) {
    /**
     * @return Valid JWT token
     */
    public String login(String username, String password) {
        if (username == null || password == null)
            throw new CannotAuthorizeException("Username and password should be set");
        var account = accountService.getAccount(username);

        if (account == null || !passwordEncoder.matches(password, account.password()))
            throw new CannotAuthorizeException("Invalid username or password");

        return jwtUtils.generateToken(account.id());
    }
}
