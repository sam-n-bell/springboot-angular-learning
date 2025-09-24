package com.app.sb_angular_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security configuration class for the Angular backend application.
 * 
 * This class configures:
 * - JWT-based authentication using OAuth2 Resource Server
 * - CORS settings for Angular frontend integration
 * - Request authorization rules
 * - JWT token validation with audience and issuer checks
 * 
 * The application uses Auth0 as the OAuth2 provider for authentication.
 */
@Configuration  // Marks this class as a source of bean definitions
@EnableWebSecurity  // Enables Spring Security's web security support
@EnableMethodSecurity  // Enables method-level security annotations like @PreAuthorize
public class SecurityConfig {

    // OAuth2 audience - identifies the intended recipient of the JWT token
    // This should match the audience configured in your Auth0 application
    @Value("${auth0.audience}")
    private String audience;

    // OAuth2 issuer URL - the authorization server that issued the JWT token
    // This is your Auth0 domain URL (e.g., https://your-domain.auth0.com/)
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    /**
     * Configures the main security filter chain for HTTP requests.
     * 
     * This method defines:
     * - CORS configuration for cross-origin requests from the Angular frontend
     * - CSRF protection (disabled for stateless JWT authentication)
     * - Request authorization rules (public vs protected endpoints)
     * - OAuth2 resource server configuration for JWT token validation
     * 
     * @param http HttpSecurity configuration object
     * @return SecurityFilterChain the configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS with custom configuration to allow requests from Angular frontend
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Disable CSRF protection since we're using stateless JWT authentication
            // CSRF is not needed for REST APIs that don't use session cookies
            .csrf(csrf -> csrf.disable())
            
            // Configure request authorization rules
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints - no authentication required (ORDER MATTERS!)
                .requestMatchers("/swagger-ui/**").permitAll()  // Swagger UI documentation
                .requestMatchers("/api/v1/auth/**").permitAll()  // Public authentication endpoints

                // Protected API endpoints - require valid JWT token
                .requestMatchers("/api/**").authenticated()
                
                // Allow all other requests (fallback rule)
                .anyRequest().permitAll()
            )
            
            // Configure OAuth2 Resource Server to validate JWT tokens
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    // Use custom JWT decoder with audience and issuer validation
                    .decoder(jwtDecoder())
                )
            );

        return http.build();
    }

    /**
     * Creates and configures a JWT decoder for validating OAuth2 JWT tokens.
     * 
     * This decoder:
     * 1. Automatically discovers JWKS (JSON Web Key Set) from the Auth0 issuer
     * 2. Validates the JWT signature using public keys from Auth0
     * 3. Validates the token's issuer (iss claim) matches our configured issuer
     * 4. Validates the token's audience (aud claim) matches our configured audience
     * 5. Checks token expiration and other standard JWT validations
     * 
     * @return JwtDecoder configured JWT decoder with custom validation
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Configure JWT decoder for Auth0 access tokens with typ: at+jwt
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(issuer + ".well-known/jwks.json")
            .jwsAlgorithm(org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS256)
            // Accept at+jwt token type from Auth0 RFC 9068 tokens
            .jwtProcessorCustomizer(processor -> {
                // Disable JWT type verification to accept at+jwt tokens
                processor.setJWSTypeVerifier((header, context) -> {
                    // Accept any JWT type (including at+jwt)
                });
            })
            .build();
        
        // Add audience validation
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
//        // Use the standard Spring Security JWT decoder
//        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuer);
//
//        // Add audience validation
//        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
//        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
//        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
//
//        jwtDecoder.setJwtValidator(withAudience);
//        return jwtDecoder;
        // Minimal decoder to test basic JWT validation
//        return JwtDecoders.fromIssuerLocation(issuer);
    }


    /**
     * Configures Cross-Origin Resource Sharing (CORS) settings.
     * 
     * CORS is necessary because the Angular frontend (running on localhost:4200)
     * needs to make requests to this Spring Boot backend (running on a different port).
     * Without CORS configuration, browsers would block these cross-origin requests.
     * 
     * This configuration allows:
     * - Requests from the Angular development server (localhost:4200)
     * - Common HTTP methods used by REST APIs
     * - All headers (including Authorization header for JWT tokens)
     * - Credentials to be included in requests (needed for authentication)
     * 
     * @return CorsConfigurationSource the CORS configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // allows UI to reach application
        // #TODO: set this up in properties
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        
        // OPTIONS is needed for CORS preflight requests
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Allow all headers, including custom headers and Authorization header
        configuration.setAllowedHeaders(List.of("*"));
        
        // Allow credentials (cookies, authorization headers) to be included in requests
        // This is required for JWT token authentication
        configuration.setAllowCredentials(true);
        
        // Apply this CORS configuration to all endpoints (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}