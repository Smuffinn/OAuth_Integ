package com.example.oauth2demo.controller;

import com.example.oauth2demo.model.User;
import com.example.oauth2demo.security.CustomOAuth2User;
import com.example.oauth2demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ApiController {

    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User user) {
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        userData.put("displayName", user.getDisplayName());
        userData.put("avatarUrl", user.getAvatarUrl());
        userData.put("bio", user.getBio());
        userData.put("picture", user.getAttribute("picture"));

        return ResponseEntity.ok(userData);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal CustomOAuth2User oauthUser,
            @RequestBody Map<String, String> updates) {
        
        if (oauthUser == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        String displayName = updates.get("displayName");
        String bio = updates.get("bio");

        User updatedUser = userService.updateProfile(oauthUser.getId(), displayName, bio);
        
        return ResponseEntity.ok(Map.of(
            "message", "Profile updated successfully",
            "user", updatedUser
        ));
    }
}
