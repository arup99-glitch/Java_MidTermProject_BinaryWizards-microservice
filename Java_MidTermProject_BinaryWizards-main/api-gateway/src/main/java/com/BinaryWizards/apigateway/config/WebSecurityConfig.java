package com.BinaryWizards.apigateway.config;

import com.BinaryWizards.apigateway.filter.JwtWebFilter;
import com.BinaryWizards.apigateway.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtWebFilter jwtWebFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .authorizeExchange()
                .pathMatchers("/book-inventory/**").hasAuthority(Role.BOOKSERVICE.name())
                .pathMatchers("/book-service/book/buy").hasAuthority(Role.USER.name())
                .pathMatchers("/auth-server/register", "/auth-server/login").permitAll()
                .pathMatchers( "/book-service/create","/book-service/update/{id}", "/book-service/delete/{id}").hasAuthority(Role.ADMIN.name())
                .pathMatchers("/book-service/book/all", "/book-service/book/{id}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .anyExchange()
                .authenticated()
                .and()
                .addFilterBefore(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .formLogin().disable()
                .httpBasic().disable()
                .build();
    }

}