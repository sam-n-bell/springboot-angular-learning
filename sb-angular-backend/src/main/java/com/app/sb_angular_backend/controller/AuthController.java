package com.app.sb_angular_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test(Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Authentication successful!");
        response.put("user", authentication.getName());
        response.put("authorities", authentication.getAuthorities().toString());
        return ResponseEntity.ok(response);
    }

    // Debug endpoint to inspect JWT tokens - bypasses authentication
    @GetMapping("/debug/token")
    public ResponseEntity<?> debugToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String[] tokenParts = token.split("\\.");
            return ResponseEntity.ok(Map.of(
                "tokenLength", token.length(),
                "tokenPrefix", token.substring(0, Math.min(50, token.length())),
                "tokenParts", tokenParts.length,
                "isLikelyJWE", tokenParts.length == 5, // JWE has 5 parts, JWT has 3
                "isLikelyJWT", tokenParts.length == 3
            ));
        }
        return ResponseEntity.ok(Map.of("error", "No Authorization header found"));
    }

}