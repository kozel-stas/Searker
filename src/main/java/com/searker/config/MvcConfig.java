package com.searker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods(
                                RequestMethod.GET.name(),
                                RequestMethod.HEAD.name(),
                                RequestMethod.OPTIONS.name(),
                                RequestMethod.POST.name()
                        )
                        .allowedOrigins(CrossOrigin.DEFAULT_ORIGINS)
                        .allowedHeaders(CrossOrigin.DEFAULT_ALLOWED_HEADERS);
            }
        };
    }

}
