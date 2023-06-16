package com.project.websecurityapp.init;

import com.project.websecurityapp.models.Role;
import com.project.websecurityapp.models.User;

import com.project.websecurityapp.service.RoleServiceImp;
import com.project.websecurityapp.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseInitial {

    private final UserServiceImp userService;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public DatabaseInitial(UserServiceImp userService, RoleServiceImp roleServiceImp) {
        this.userService = userService;
        this.roleServiceImp = roleServiceImp;
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
        roleServiceImp.save(roleAdmin);
        roleServiceImp.save(roleUser);

        User admin = new User("Alex", "admin@mail.ru",
                "admin",
                setAdminRole()); // ������: admin
        User user = new User("Max", "user@mail.ru",
                "user",
                setRoleUser()); // ������: user

        userService.saveUser(admin);
        userService.saveUser(user);
    }

}
