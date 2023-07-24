package com.onlinebank.dto.response;

import com.onlinebank.models.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String address;
    private String username;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.first_name = customer.getFirstName();
        this.last_name = customer.getLastName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.username = customer.getUsername();
    }
}
