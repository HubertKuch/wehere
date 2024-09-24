package com.hubertkuch.wehere.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    private String secret = "";

    public JWTUtils(@Value("jwt_secret") String secret) {
        this.secret = secret;
    }

    public String generateToken(String accountId) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("id", accountId)
                .withIssuedAt(new Date())
                .withIssuer("WeHere")
                .sign(Algorithm.HMAC256(secret));
    }

    public String verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getClaim("id").asString();
    }
}
