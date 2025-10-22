package com.example.oauth2demo.config;

import com.example.oauth2demo.security.CustomOAuth2UserService;
import com.example.oauth2demo.security.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;

    public SecurityConfig(CustomOAuth2UserService oauth2UserService, 
                          OAuth2AuthenticationSuccessHandler successHandler) {
        this.oauth2UserService = oauth2UserService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .authorizeRequests()
                .antMatchers("/", "/login**", "/error**", "/webjars/**", "/css/**", "/js/**", "/api/**", 
                             "/oauth-error", "/logout-success", "/access-denied", "/session-timeout", "/session-expired").permitAll()
                .anyRequest().authenticated()
                .and()
            .oauth2Login()
                .loginPage("/")
                .userInfoEndpoint()
                    .userService(oauth2UserService)
                    .and()
                .successHandler(successHandler)
                .failureUrl("/oauth-error")
                .and()
            .logout()
                .logoutSuccessUrl("/logout-success")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                    "/logout",
                    "/login/oauth2/code/**"
                )
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/session-timeout")
                .maximumSessions(1)
                .expiredUrl("/session-expired");
                
        return http.build();
    }
}
