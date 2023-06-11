package com.project.websecurityapp.controllers;

import com.project.websecurityapp.models.User;
import com.project.websecurityapp.repository.UserRepository;
import com.project.websecurityapp.service.RoleServiceImp;
import com.project.websecurityapp.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
   private final RoleServiceImp roleServiceImp;

    @Autowired
    public AdminController(UserServiceImp userService, RoleServiceImp roleServiceImp) {
        this.userService = userService;

        this.roleServiceImp = roleServiceImp;
    }
    @GetMapping("/admin")
    public String pageToViewAllUsers(ModelMap model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("roleUser", roleServiceImp.getAllRoles());
        return "admin";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User user,
                          @ModelAttribute("role") Long roleId) {
        return getString(user, roleId);
    }

    private String getString(@ModelAttribute("newUser") User user,
                             @ModelAttribute("role") Long roleId) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setUsername(user.getEmail());
        user.setRoles(Set.of(roleServiceImp.getOneRole(roleId)));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String prepareToUpdateUser(Model model, @PathVariable("id") Long id) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("roleUser", roleServiceImp.getAllRoles());
        return "admin";
    }
    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @ModelAttribute("role") Long roleId) {
        return getString(user, roleId);
    }

    @GetMapping("/delete/{id}")
    public String prepareToDeleteUser(Model model, @PathVariable("id") Long id) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("roleUser", roleServiceImp.getAllRoles());
        return "admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
