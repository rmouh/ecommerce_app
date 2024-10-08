package com.example.e_commerce.config; // Changez le nom du package selon votre projet

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Permet les requêtes CORS sur tous les endpoints commençant par /api
                .allowedOrigins("http://localhost:3000") // URL de votre frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true); // Autoriser l'envoi de cookies
    }
}
