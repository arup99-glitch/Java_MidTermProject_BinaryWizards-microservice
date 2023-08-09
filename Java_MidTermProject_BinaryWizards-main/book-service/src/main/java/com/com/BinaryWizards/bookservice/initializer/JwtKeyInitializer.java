package com.com.BinaryWizards.bookservice.initializer;

import com.com.BinaryWizards.bookservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtKeyInitializer implements CommandLineRunner {

    private final JwtService jwtService;

    @Override
    public void run(String... args) throws Exception {
        jwtService.generateToken();
    }

}
