package com.example.oauth2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RedirectController {

    private static final String REACT_BASE_URL = "http://localhost:3000";

    @GetMapping("/oauth-error")
    public void oauthError(HttpServletResponse response) throws IOException {
        response.sendRedirect(REACT_BASE_URL + "/?error=oauth");
    }

    @GetMapping("/logout-success")
    public void logoutSuccess(HttpServletResponse response) throws IOException {
        response.sendRedirect(REACT_BASE_URL + "/?logout=success");
    }

    @GetMapping("/access-denied")
    public void accessDenied(HttpServletResponse response) throws IOException {
        response.sendRedirect(REACT_BASE_URL + "/?error=access_denied");
    }

    @GetMapping("/session-timeout")
    public void sessionTimeout(HttpServletResponse response) throws IOException {
        response.sendRedirect(REACT_BASE_URL + "/?error=session_timeout");
    }

    @GetMapping("/session-expired")
    public void sessionExpired(HttpServletResponse response) throws IOException {
        response.sendRedirect(REACT_BASE_URL + "/?error=session_expired");
    }
}
