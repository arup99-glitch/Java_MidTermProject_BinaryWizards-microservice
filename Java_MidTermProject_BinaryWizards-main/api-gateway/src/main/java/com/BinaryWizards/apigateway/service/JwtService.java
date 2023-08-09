package com.BinaryWizards.apigateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final Key SECRET_KEY;

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

}

