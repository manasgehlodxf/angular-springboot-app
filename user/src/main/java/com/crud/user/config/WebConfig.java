package com.crud.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply CORS to the '/api' path and sub-paths
            .allowedOrigins("http://localhost:4200")  // Allow requests from the React app (default React dev server port is 3000)
            .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow these HTTP methods
            .allowedHeaders("*");  // Allow all headers
    }
}
