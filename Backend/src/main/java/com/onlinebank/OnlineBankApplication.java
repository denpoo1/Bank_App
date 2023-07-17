package com.onlinebank;

import com.onlinebank.models.Account;
import com.onlinebank.models.Customer;
import com.onlinebank.services.AccountService;
import com.onlinebank.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
public class OnlineBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBankApplication.class, args);
    }
}
