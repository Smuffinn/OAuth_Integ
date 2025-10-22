package com.example.oauth2demo.security;

import com.example.oauth2demo.model.AuthProvider;
import com.example.oauth2demo.model.User;
import com.example.oauth2demo.service.UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        // Determine the provider (Google, GitHub, etc.)
        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        AuthProvider.ProviderType provider = AuthProvider.ProviderType.valueOf(registrationId);
        
        // Extract user attributes based on the provider
        String email = null;
        String name = null;
        String avatarUrl = null;
        String providerUserId = null;
        
        if (provider == AuthProvider.ProviderType.GOOGLE) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            avatarUrl = oAuth2User.getAttribute("picture");
            providerUserId = oAuth2User.getAttribute("sub");
        } else if (provider == AuthProvider.ProviderType.GITHUB) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            if (name == null) {
                name = oAuth2User.getAttribute("login"); // Use GitHub username if name is not available
            }
            
            Map<String, Object> attributes = oAuth2User.getAttributes();
            if (attributes.containsKey("avatar_url")) {
                avatarUrl = (String) attributes.get("avatar_url");
            }
            
            Object idAttr = oAuth2User.getAttribute("id");
            providerUserId = idAttr != null ? idAttr.toString() : null;
            
            if (providerUserId == null) {
                throw new OAuth2AuthenticationException("Could not extract user ID from GitHub");
            }
        }
        
        // Process the OAuth2 user
        User user = userService.processOAuth2User(provider, providerUserId, email, name, avatarUrl);
        
        return new CustomOAuth2User(user, oAuth2User.getAttributes());
    }
}
