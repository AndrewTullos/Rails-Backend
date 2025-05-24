package org.rails.controllers;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecretSigningKey {

    private SecretKey key;

    public SecretSigningKey() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public SecretKey getKey() {
        return key;
    }
}
