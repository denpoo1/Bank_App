package com.onlinebank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author Denis Durbalov
 */
@SpringBootApplication
@OpenAPIDefinition
public class OnlineBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineBankApplication.class, args);
    }
}
