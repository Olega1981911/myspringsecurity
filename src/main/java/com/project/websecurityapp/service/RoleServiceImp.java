package com.project.websecurityapp.service;

import com.project.websecurityapp.models.Role;
import com.project.websecurityapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void save(Role entity) {
        roleRepository.save(entity);
    }


    public Role getOneRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }
}
