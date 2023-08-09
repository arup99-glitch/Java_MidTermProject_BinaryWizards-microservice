package com.BinaryWizards.authenticationserver.dataintializer;

import com.BinaryWizards.authenticationserver.entity.RoleEntity;
import com.BinaryWizards.authenticationserver.repository.RoleRepository;
import com.BinaryWizards.authenticationserver.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<RoleEntity> saved = roleRepository.saveAll(List.of(new RoleEntity(Role.USER), new RoleEntity(Role.ADMIN)));
        System.out.println("Roles Added to Database: "+saved);
    }

}
