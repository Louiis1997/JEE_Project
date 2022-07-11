package com.esgi.algoBattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AlgoBattle {
    public static void main(String[] args) {
        System.out.println("Hello Spring!");
        SpringApplication.run(AlgoBattle.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:4201");
            }
        };
    }
}