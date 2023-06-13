package com.project.websecurityapp.service;

import com.project.websecurityapp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByName(String username);

    List<User> findAll();

    User findOne(long id);

    void saveUser(User user);

    void update(long id, User updateUser);

    void deleteUser(long id);
}
