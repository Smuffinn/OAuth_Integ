package com.example.oauth2demo.service;

import com.example.oauth2demo.model.AuthProvider;
import com.example.oauth2demo.model.User;
import com.example.oauth2demo.repository.AuthProviderRepository;
import com.example.oauth2demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthProviderRepository authProviderRepository;

    @Transactional
    public User processOAuth2User(AuthProvider.ProviderType provider, String providerUserId, String email, String name, String avatarUrl) {
        // Check if this user already exists by their OAuth2 provider user ID
        Optional<AuthProvider> existingAuthProvider = authProviderRepository
                .findByProviderAndProviderUserId(provider, providerUserId);

        if (existingAuthProvider.isPresent()) {
            // User exists, update their details if needed
            User user = existingAuthProvider.get().getUser();
            
            // Update user details if they've changed on the provider side
            boolean updated = false;
            if (user.getEmail() == null || !user.getEmail().equals(email)) {
                user.setEmail(email);
                updated = true;
            }
            if (name != null && (user.getDisplayName() == null || !user.getDisplayName().equals(name))) {
                user.setDisplayName(name);
                updated = true;
            }
            if (avatarUrl != null && (user.getAvatarUrl() == null || !user.getAvatarUrl().equals(avatarUrl))) {
                user.setAvatarUrl(avatarUrl);
                updated = true;
            }
            
            if (updated) {
                userRepository.save(user);
            }
            
            return user;
        } else {
            // User doesn't exist, create a new user
            User user = new User();
            user.setEmail(email);
            user.setDisplayName(name);
            user.setAvatarUrl(avatarUrl);
            
            // Create and associate the auth provider
            AuthProvider newAuthProvider = new AuthProvider();
            newAuthProvider.setProvider(provider);
            newAuthProvider.setProviderUserId(providerUserId);
            newAuthProvider.setProviderEmail(email);
            newAuthProvider.setUser(user);
            
            user.getAuthProviders().add(newAuthProvider);
            
            return userRepository.save(user);
        }
    }

    @Transactional
    public User updateProfile(Long userId, String displayName, String bio) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (displayName != null && !displayName.isEmpty()) {
            user.setDisplayName(displayName);
        }
        
        user.setBio(bio);
        
        return userRepository.save(user);
    }
}
