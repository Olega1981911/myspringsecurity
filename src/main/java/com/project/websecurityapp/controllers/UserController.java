package com.project.websecurityapp.controllers;


import com.project.websecurityapp.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserServiceImp userService;

    @Autowired
    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserPage(Principal principal, Model model) {
        model.addAttribute("users", userService.getUserByUsername(principal.getName()));
        return "user";
    }

}
