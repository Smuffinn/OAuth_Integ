package com.example.oauth2demo.repository;

import com.example.oauth2demo.model.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Long> {
    Optional<AuthProvider> findByProviderAndProviderUserId(AuthProvider.ProviderType provider, String providerUserId);
    boolean existsByProviderAndProviderUserId(AuthProvider.ProviderType provider, String providerUserId);
}
