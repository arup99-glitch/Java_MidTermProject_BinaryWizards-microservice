package com.BinaryWizards.authenticationserver.service;

import com.BinaryWizards.authenticationserver.entity.RoleEntity;
import com.BinaryWizards.authenticationserver.role.Role;

public interface RoleService {

    RoleEntity getRole(Role role);

}
