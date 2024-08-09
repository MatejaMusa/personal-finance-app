package com.matejamusa.personal_finance.service;

import com.matejamusa.personal_finance.exception.ApiException;
import com.matejamusa.personal_finance.form.SignupAndLoginForm;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository<User> userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupAndLoginForm input) {
        User user = User.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .build();

        return userRepository.create(user);
    }

    public User authenticate(SignupAndLoginForm input) {
        authenticationManager.authenticate(unauthenticated(
                        input.getUsername(),
                        input.getPassword())
        );

        return userRepository.getUserByUsername(input.getUsername());
    }
}
