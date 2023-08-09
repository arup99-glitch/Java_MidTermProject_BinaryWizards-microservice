package com.BinaryWizards.authenticationserver.repository;

import com.BinaryWizards.authenticationserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserDetails findByEmail(String email);

}
