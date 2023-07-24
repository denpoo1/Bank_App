package com.onlinebank.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
/**
 * @author Denis Durbalov
 */
@Data
public class AuthorizationRequest {
    @NotEmpty(message = "username must be not empty")
    private String username;
    @NotEmpty(message = "password must be not empty")
    private String password;

}
