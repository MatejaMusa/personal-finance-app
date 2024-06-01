package com.matejamusa.personal_finance.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupAndLoginForm {
    @NotEmpty(message = "The username is required.")
    private String username;

    @NotEmpty(message = "The password is required.")
    private String password;
}
