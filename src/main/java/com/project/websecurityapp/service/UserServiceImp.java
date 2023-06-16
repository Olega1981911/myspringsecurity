package com.project.websecurityapp.service;


import com.project.websecurityapp.models.Role;
import com.project.websecurityapp.models.User;
import com.project.websecurityapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {


    private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;


        this.passwordEncoder = passwordEncoder;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new User(user.getUsername(),user.getPassword(), (Set<Role>) roles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> roles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User findByName(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(long id, User updateUser) {
        updateUser.setId(id);
        userRepository.save(updateUser);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }
}
