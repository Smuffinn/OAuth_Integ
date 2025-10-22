package com.example.oauth2demo.model;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "auth_providers")
public class AuthProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType provider;
    
    @Column(name = "provider_user_id", nullable = false)
    private String providerUserId;
    
    @Column(name = "provider_email")
    private String providerEmail;
    
    public enum ProviderType {
        GOOGLE,
        GITHUB
    }
}
