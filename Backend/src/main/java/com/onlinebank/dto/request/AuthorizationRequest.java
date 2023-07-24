package com.onlinebank.dto.request;

import com.onlinebank.models.Customer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthorizationRequest {
    @NotEmpty(message = "username must be not empty")
    private String username;
    @NotEmpty(message = "password must be not empty")
    private String password;

}
