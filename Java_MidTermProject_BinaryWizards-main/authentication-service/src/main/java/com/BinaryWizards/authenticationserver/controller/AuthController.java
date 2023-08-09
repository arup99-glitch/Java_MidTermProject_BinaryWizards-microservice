package com.BinaryWizards.authenticationserver.controller;

import com.BinaryWizards.authenticationserver.model.LoginRequest;
import com.BinaryWizards.authenticationserver.model.RegistrationRequest;
import com.BinaryWizards.authenticationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth-server")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest){
        return userService.register(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

}
