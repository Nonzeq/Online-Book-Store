package com.kobylchak.bookstore.controller;

import com.kobylchak.bookstore.dto.user.UserLoginRequestDto;
import com.kobylchak.bookstore.dto.user.UserLoginResponseDto;
import com.kobylchak.bookstore.dto.user.UserRegistrationRequestDto;
import com.kobylchak.bookstore.dto.user.UserResponseDto;
import com.kobylchak.bookstore.exception.RegistrationException;
import com.kobylchak.bookstore.service.user.AuthenticationService;
import com.kobylchak.bookstore.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    private UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
