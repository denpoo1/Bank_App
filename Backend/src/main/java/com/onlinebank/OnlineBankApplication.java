package com.onlinebank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
