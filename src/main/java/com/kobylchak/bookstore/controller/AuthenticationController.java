package com.kobylchak.bookstore.controller;

import com.kobylchak.bookstore.dto.user.UserLoginRequestDto;
import com.kobylchak.bookstore.dto.user.UserLoginResponseDto;
import com.kobylchak.bookstore.dto.user.UserRegistrationRequestDto;
import com.kobylchak.bookstore.dto.user.UserResponseDto;
import com.kobylchak.bookstore.exception.RegistrationException;
import com.kobylchak.bookstore.service.auth.AuthenticationService;
import com.kobylchak.bookstore.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ResponseStatus(HttpStatus.OK)
    private UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
