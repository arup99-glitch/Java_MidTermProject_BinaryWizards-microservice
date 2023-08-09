package com.BinaryWizards.apigateway.config;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.security.Key;
import java.util.Base64;

@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("jwt")
public class SecretKeyConfig {
    private String secretkeybase64;

    @Bean
    public Key secretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretkeybase64));
    }

}

