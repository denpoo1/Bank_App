package com.onlinebank.dto;

import com.onlinebank.models.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CustomerRequest {

    @NotEmpty
    @Length(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "The string First name must not contain special characters.")
    private String first_name;

    @NotEmpty
    @Length(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "The string Last name must not contain special characters.")
    private String last_name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Length(max = 15)
    private String phone;

    @NotEmpty
    @Length(max = 50)
    private String address;

    @NotEmpty
    @Length(max = 50)
    private String password;

    @NotEmpty
    @Length(max = 50)
    private String username;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(this.first_name);
        customer.setLastName(this.last_name);
        customer.setEmail(this.email);
        customer.setPhone(this.phone);
        customer.setAddress(this.address);
        customer.setPassword(this.password);
        customer.setUsername(this.username);
        return customer;
    }
}
