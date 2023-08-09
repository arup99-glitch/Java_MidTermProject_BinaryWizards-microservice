package com.BinaryWizards.authenticationserver.service.concrete;

import com.BinaryWizards.authenticationserver.entity.*;
import com.BinaryWizards.authenticationserver.exception.InvalidRoleException;
import com.BinaryWizards.authenticationserver.mapper.Mapper;
import com.BinaryWizards.authenticationserver.model.*;
import com.BinaryWizards.authenticationserver.repository.UserRepository;
import com.BinaryWizards.authenticationserver.role.Role;
import com.BinaryWizards.authenticationserver.service.*;
import com.BinaryWizards.authenticationserver.validator.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Mapper mapper;

    public ResponseEntity<?> register(@Valid RegistrationRequest registrationRequest) {
        UserEntity userEntity = mapper.mapToRegisteredUserEntity(registrationRequest);
        Set<RoleEntity> roles = new HashSet<>();
        for (Role role : registrationRequest.getRoles()) {
            RoleEntity roleEntity = roleService.getRole(role);
            if (roleEntity == null) {
                throw new InvalidRoleException(role.name());
            }
            roles.add(roleEntity);
        }
        userEntity.setRoles(roles);
        userEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword().trim()));
        if(userRepository.findByEmail(userEntity.getEmail())!=null){
            return new ResponseEntity<>(RegistrationResponse.builder()
                    .success(false)
                    .message(List.of(MessageConstants.EMAIL_EXISTS))
                    .user(mapper.mapToRegistrationRequest(userEntity))
                    .build(), HttpStatus.BAD_REQUEST);
        }
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return new ResponseEntity<>(RegistrationResponse.builder()
                .success(true)
                .message(List.of(MessageConstants.REGISTRATION_SUCCESS))
                .token(jwtService.generateJwtToken(savedUserEntity))
                .user(mapper.mapToRegistrationRequest(savedUserEntity))
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        String email =loginRequest.getEmail().trim();
        String password=loginRequest.getPassword().trim();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (!authentication.isAuthenticated()) {
            return new ResponseEntity<>(LoginResponse.builder()
                    .success(false)
                    .message(List.of(MessageConstants.LOGIN_FAILED))
                    .build(), HttpStatus.BAD_REQUEST);
        }
        String jwtToken = jwtService.generateJwtToken(authentication);
        if (jwtToken == null) {
            return new ResponseEntity<>(LoginResponse.builder()
                    .success(false)
                    .message(List.of(MessageConstants.JWT_TOKEN_GENERATION_FAILED))
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(LoginResponse.builder()
                .success(true)
                .message(List.of(MessageConstants.LOGIN_SUCCESSFUL))
                .token(jwtToken)
                .build(), HttpStatus.OK);
    }

}
