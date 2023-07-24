package com.onlinebank.dto.response;

import lombok.Data;

/**
 * @author Denis Durbalov
 */
@Data
public class AuthorizationResponse {
    private String token;

    public AuthorizationResponse(String token) {
        this.token = token;
    }
}
