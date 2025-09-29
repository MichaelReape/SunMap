package com.sunmap.app.config; // package: place this under your app's base package

import org.springframework.context.annotation.Bean; // @Bean annotation for Spring-managed beans
import org.springframework.context.annotation.Configuration; // @Configuration to mark this as a config class
import org.springframework.web.servlet.config.annotation.CorsRegistry; // CORS registry type
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // MVC config hook

@Configuration // tells Spring this class provides configuration
public class CorsConfig {

    @Bean // registers a bean that customizes Spring MVC
    public WebMvcConfigurer corsConfigurer() {
        // Return an implementation that adds CORS rules
        return new WebMvcConfigurer() {
            @SuppressWarnings("null")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**") // apply CORS to all endpoints in your API
                        .allowedOrigins("http://localhost:5173") // allow your Vite dev server origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP verbs your frontend may use
                        .allowedHeaders("*") // allow all headers from the browser request
                        .exposedHeaders("Location") // example: expose Location for POST-created resources
                        .allowCredentials(true) // keep false unless you need cookies/auth across origins
                        .maxAge(3600); // cache preflight (OPTIONS) responses for 1 hour
            }
        };
    }
}
