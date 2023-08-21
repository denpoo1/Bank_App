package com.onlinebank.dto.response;

import com.onlinebank.models.CustomerModel;
import lombok.Data;

/**
 * @author Denis Durbalov
 */
@Data
public class CustomerResponse {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String address;
    private String username;
    private String phone;

    public CustomerResponse(CustomerModel customerModel) {
        this.id = customerModel.getId();
        this.first_name = customerModel.getFirstName();
        this.last_name = customerModel.getLastName();
        this.email = customerModel.getEmail();
        this.address = customerModel.getAddress();
        this.username = customerModel.getUsername();
        this.phone = customerModel.getPhone();
    }
}
