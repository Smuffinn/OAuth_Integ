package com.example.oauth2demo.controller;

import com.example.oauth2demo.model.User;
import com.example.oauth2demo.security.CustomOAuth2User;
import com.example.oauth2demo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal CustomOAuth2User oauthUser,
            @RequestParam(required = false) String displayName,
            @RequestParam(required = false) String bio) {
        
        if (oauthUser != null) {
            userService.updateProfile(oauthUser.getId(), displayName, bio);
        }
        
        return "redirect:/profile";
    }
}
