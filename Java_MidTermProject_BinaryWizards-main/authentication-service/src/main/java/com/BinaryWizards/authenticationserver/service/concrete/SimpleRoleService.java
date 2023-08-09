package com.BinaryWizards.authenticationserver.service.concrete;

import com.BinaryWizards.authenticationserver.entity.RoleEntity;
import com.BinaryWizards.authenticationserver.repository.RoleRepository;
import com.BinaryWizards.authenticationserver.role.Role;
import com.BinaryWizards.authenticationserver.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleRoleService implements RoleService {

    private final RoleRepository roleRepository;
    public RoleEntity getRole(Role role){
        return roleRepository.findByRole(role);
    }

}
