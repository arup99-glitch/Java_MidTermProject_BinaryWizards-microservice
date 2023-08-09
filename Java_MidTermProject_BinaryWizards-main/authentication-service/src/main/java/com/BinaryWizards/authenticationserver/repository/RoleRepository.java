package com.BinaryWizards.authenticationserver.repository;

import com.BinaryWizards.authenticationserver.entity.RoleEntity;
import com.BinaryWizards.authenticationserver.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByRole(Role role);

}
