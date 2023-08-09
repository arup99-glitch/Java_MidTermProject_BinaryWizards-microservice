package com.BinaryWizards.authenticationserver.service.concrete;

import com.BinaryWizards.authenticationserver.exception.UserNotFoundByEmailException;
import com.BinaryWizards.authenticationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails=userRepository.findByEmail(username);
        if(userDetails==null) throw new UserNotFoundByEmailException(username);
        return userDetails;
    }

}
