package com.onlinebank.dto.request;

import com.onlinebank.models.CustomerModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
/**
 * @author Denis Durbalov
 */
@Data
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
    private String password;

    @NotEmpty
    @Length(max = 50)
    private String username;

    public CustomerModel toCustomer() {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstName(this.first_name);
        customerModel.setLastName(this.last_name);
        customerModel.setEmail(this.email);
        customerModel.setPhone(this.phone);
        customerModel.setAddress(this.address);
        customerModel.setPassword(this.password);
        customerModel.setUsername(this.username);
        return customerModel;
    }
}
