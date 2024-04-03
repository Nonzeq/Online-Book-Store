package com.kobylchak.bookstore.service.auth;

import com.kobylchak.bookstore.dto.user.UserLoginRequestDto;
import com.kobylchak.bookstore.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
