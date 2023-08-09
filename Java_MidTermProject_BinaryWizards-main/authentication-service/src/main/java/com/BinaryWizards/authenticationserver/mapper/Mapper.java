package com.BinaryWizards.authenticationserver.mapper;

import com.BinaryWizards.authenticationserver.entity.UserEntity;
import com.BinaryWizards.authenticationserver.model.RegistrationRequest;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public RegistrationRequest mapToRegistrationRequest(UserEntity userEntity) {
        return RegistrationRequest.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .roles(userEntity.getRoles().stream()
                                .map(roleEntity -> roleEntity.getRole())
                                .collect(Collectors.toSet()))
                .build();
    }

    public UserEntity mapToRegisteredUserEntity(RegistrationRequest registrationRequest) {
        return UserEntity.builder()
                .firstName(registrationRequest.getFirstName().trim())
                .lastName(registrationRequest.getLastName().trim())
                .email(registrationRequest.getEmail().trim())
                .phone(registrationRequest.getPhone().trim())
                .address(registrationRequest.getAddress().trim())
                .dob(registrationRequest.getDob())
                .build();
    }

}
