package com.matejamusa.personal_finance.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class LoginResponse {
    private String token;
    private long expiresIn;
}
