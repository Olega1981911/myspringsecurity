package com.project.websecurityapp.service;

import com.project.websecurityapp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User getUserByUsername(String username);
    public List<User> findAll();
    public User findOne(long id);
    public void saveUser(User user);
    public void update(long id, User updateUser);
    public void deleteUser(long id);
}
