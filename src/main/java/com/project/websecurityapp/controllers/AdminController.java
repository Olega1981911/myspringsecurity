package com.project.websecurityapp.controllers;

import com.project.websecurityapp.models.User;
import com.project.websecurityapp.repository.RoleRepository;
import com.project.websecurityapp.repository.UserRepository;
import com.project.websecurityapp.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;

    private final UserRepository userRepository;
   private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserServiceImp userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        return "admin";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("allUsers", userRepository.findById(id));
        return "/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("users") User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "/edit";

       userService.loadUserByUsername(user.getUsername());
        return "redirect:/admin";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }
}
