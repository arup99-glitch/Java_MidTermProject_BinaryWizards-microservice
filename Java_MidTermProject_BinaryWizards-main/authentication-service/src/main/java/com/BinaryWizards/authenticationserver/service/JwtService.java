package com.BinaryWizards.authenticationserver.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateJwtToken(Authentication authentication);
    String generateJwtToken(UserDetails userDetails);

}
