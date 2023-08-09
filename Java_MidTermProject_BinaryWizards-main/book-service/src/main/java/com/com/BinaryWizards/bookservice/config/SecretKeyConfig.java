package com.com.BinaryWizards.bookservice.config;//package com.example.authenticationservice.config;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

