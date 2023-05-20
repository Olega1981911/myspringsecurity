package com.project.websecurityapp.init;

import com.project.websecurityapp.models.Role;
import com.project.websecurityapp.models.User;
import com.project.websecurityapp.repository.RoleRepository;
import com.project.websecurityapp.service.UserServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitial {

    private final UserServiceImp userService;
    private final RoleRepository roleRepository;

    public DatabaseInitial(UserServiceImp userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    private final Role roleAdmin = new Role("ROLE_ADMIN");
    private final Role roleUser = new Role("ROLE_USER");

    public Set<Role> setAdminRole() {
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(roleAdmin);
        return adminRole;
    }
    public Set<Role> setRoleUser() {
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleUser);
        return userRole;
    }

    @Transactional
    @Bean
    public void addInitUsers() {
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        User admin = new User( "Petya", "admin@mail.ru",
                "a0UQ3N432",
                setAdminRole()); // ������: admin
        User user = new User("Max", "user@mail.ru",
                "DYmBEba8.87q",
                setRoleUser()); // ������: user

        userService.loadUserByUsername(String.valueOf(admin));
        userService.loadUserByUsername(String.valueOf(user));
    }

}
