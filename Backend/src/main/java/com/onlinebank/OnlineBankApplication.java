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
        ConfigurableApplicationContext context = SpringApplication.run(OnlineBankApplication.class, args);
        CustomerService customerService = context.getBean(CustomerService.class);
        AccountService accountService = context.getBean(AccountService.class);


        // Создание и сохранение нового клиента
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", "123456789", "New York", "password123", "johndoe");
        customerService.saveCustomer(customer);

        // Получение клиента по его ID
        Customer retrievedCustomer = customerService.getCustomerById(customer.getId());
        System.out.println("Retrieved Customer: " + retrievedCustomer);

        // Обновление информации о клиенте
        retrievedCustomer.setEmail("john.doe@gmail.com");
        customerService.updateCustomer(retrievedCustomer);

        // Получение обновленной информации о клиенте
        Customer retrievedUpdatedCustomer = customerService.getCustomerById(retrievedCustomer.getId());
        System.out.println("Updated Customer: " + retrievedUpdatedCustomer);

        Account account = new Account(new Date(), 1);
        account.setCustomer(customer);
        accountService.saveAccount(account);

        // Удаление клиента
        System.out.println(retrievedCustomer.getId());
        customerService.deleteCustomerById(retrievedCustomer.getId());

        // Закрытие контекста приложения
        context.close();
    }
}
