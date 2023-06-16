package com.project.websecurityapp.controllers;

import com.project.websecurityapp.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/login")
public class AuthController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


}
