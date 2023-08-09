package com.BinaryWizards.apigateway.filter;

import com.BinaryWizards.apigateway.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtWebFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = extractTokenFromRequest(exchange.getRequest());
        if (StringUtils.hasText(token)) {
            Claims claims = jwtService.parseClaims(token);
            if (claims.getExpiration().after(new Date(System.currentTimeMillis()))) {
                Context context = ReactiveSecurityContextHolder.withAuthentication(getAuthentication(claims));
                return chain.filter(exchange).contextWrite(context);
            }
        }
        return chain.filter(exchange);
    }

    private String extractTokenFromRequest(ServerHttpRequest serverHttpRequest) {
        String authorization = serverHttpRequest.getHeaders().getFirst("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7);
    }

    public Authentication getAuthentication(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }

}
