package com.app.sb_angular_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger) configuration for API documentation with Auth0 OAuth2 authentication.
 * 
 * This configuration:
 * - Sets up basic API information (title, version, description)
 * - Configures OAuth2 authentication with Auth0 for Swagger UI
 * - Enables the "Authorize" button in Swagger UI for OAuth2 flow
 * - Applies OAuth2 security to all endpoints by default
 * 
 * Users can authenticate by:
 * 1. Clicking the "Authorize" button in Swagger UI
 * 2. Being redirected to Auth0 login page
 * 3. Logging in with their Auth0 credentials
 * 4. Being redirected back to Swagger UI with a valid token
 * 5. Testing protected endpoints directly from the Swagger interface
 * 
 * Note: Make sure your Auth0 application is configured to allow 
 * http://localhost:8080/swagger-ui/oauth2-redirect.html as a callback URL
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Spring Boot Angular Backend API",
        version = "1.0.0",
        description = "REST API for the Angular frontend application with OAuth2 authentication via Auth0"
    ),
    // Apply OAuth2 security requirement to all endpoints by default
    security = @SecurityRequirement(name = "oauth2")
)
@SecurityScheme(
    name = "oauth2",
    type = SecuritySchemeType.OAUTH2,
    description = "Authenticate using OAuth2 with Auth0. Click 'Authorize' to log in through Auth0.",
    flows = @OAuthFlows(
        authorizationCode = @OAuthFlow(
            // Auth0 authorization endpoint - users will be redirected here to log in
            // Audience is needed so auth0 knows which application the token is for
            authorizationUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}authorize?audience=http://localhost:8080/api",
            
            // Auth0 token endpoint - where Swagger UI exchanges authorization code for tokens
            tokenUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}oauth/token",
            
            // Define the OAuth2 scopes that are needed
            // These should match what's configured in the Auth0 application permissions
            scopes = {
                @io.swagger.v3.oas.annotations.security.OAuthScope(
                            name = "read:employees",
                            description = "Read Employee Data"
                ),
                @io.swagger.v3.oas.annotations.security.OAuthScope(
                        name = "read:places",
                        description = "Read places Data"
                )
            }
        )
    )
)
public class OpenApiConfig {
    // The OAuth2 configuration is handled by the annotations above
    // Additional properties are already configured in application.properties:
}
