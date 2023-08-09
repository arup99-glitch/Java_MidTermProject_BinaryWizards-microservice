package com.BinaryWizards.authenticationserver.service.concrete;

import com.BinaryWizards.authenticationserver.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleJwtService implements JwtService {
    private final Key SECRET_KEY;

    @Override
    public String generateJwtToken(Authentication authentication) {
        final long issueTime = System.currentTimeMillis();
        return Jwts.builder()
                .addClaims(Map.of(
                        "roles",
                        authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet())
                ))
                .setSubject(((UserDetails) authentication.getPrincipal()).getUsername())
                .setIssuedAt(new Date(issueTime))
                .setExpiration(new Date(issueTime + 1000 * 60 * 60 * 24))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        final long issueTime = System.currentTimeMillis();

        return Jwts.builder()
                .addClaims(Map.of(
                        "roles",
                        userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet())
                ))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(issueTime))
                .setExpiration(new Date(issueTime + 1000 * 60 * 60 * 24))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

}
