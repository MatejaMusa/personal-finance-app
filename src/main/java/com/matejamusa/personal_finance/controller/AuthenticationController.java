package com.matejamusa.personal_finance.controller;

import com.matejamusa.personal_finance.form.SignupAndLoginForm;
import com.matejamusa.personal_finance.model.HttpResponse;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.service.AuthenticationService;
import com.matejamusa.personal_finance.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<HttpResponse> register(@RequestBody @Valid SignupAndLoginForm registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                HttpResponse.builder()
                        .data(Map.of("user", registeredUser))
                        .message(String.format("Account created for user %s", registeredUser.getUsername()))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> authenticate(@RequestBody @Valid SignupAndLoginForm loginUserDto, HttpServletResponse response) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(Map.of("user", authenticatedUser))
                        .message("Login Success")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
}