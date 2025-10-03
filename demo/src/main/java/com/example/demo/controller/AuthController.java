package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Show login page
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "login";
    }
    
    /**
     * Show registration page
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    /**
     * Process registration form
     */
    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        try {
            // Validate input
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Username is required!");
                return "register";
            }
            
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Password is required!");
                return "register";
            }
            
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Email is required!");
                return "register";
            }
            
            // Register the user
            userService.registerUser(user);
            model.addAttribute("successMessage", "Registration successful! Please login with your credentials.");
            return "login";
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred during registration. Please try again.");
            return "register";
        }
    }
}

