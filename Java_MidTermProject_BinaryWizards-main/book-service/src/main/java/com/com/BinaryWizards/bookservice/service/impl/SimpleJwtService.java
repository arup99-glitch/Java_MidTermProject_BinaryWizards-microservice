package com.com.BinaryWizards.bookservice.service.impl;

import com.com.BinaryWizards.bookservice.roles.Role;
import com.com.BinaryWizards.bookservice.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
public class SimpleJwtService implements JwtService {

    private final Key SECRET_KEY;
    private String jwtToken;

    @Override
    public void generateToken() {
        this.jwtToken="Bearer "+Jwts.builder()
                .addClaims(Map.of("roles", List.of(Role.BOOKSERVICE.name())))
                .setSubject("BookService")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(Long.MAX_VALUE))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

}
