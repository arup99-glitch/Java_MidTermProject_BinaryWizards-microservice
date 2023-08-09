package com.BinaryWizards.authenticationserver.service;

import com.BinaryWizards.authenticationserver.model.LoginRequest;
import com.BinaryWizards.authenticationserver.model.RegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * User login
     * @param loginRequest accepts LoginRequest
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> login(LoginRequest loginRequest);

    /**
     * User Registration
     * @param registrationRequest accepts RegistrationRequest
     * @return ResponseEntity<?>
     */
    ResponseEntity<?> register(RegistrationRequest registrationRequest);

}
