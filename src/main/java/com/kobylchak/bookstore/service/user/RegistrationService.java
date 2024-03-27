package com.kobylchak.bookstore.service.user;

import com.kobylchak.bookstore.dto.user.UserRegistrationRequestDto;
import com.kobylchak.bookstore.dto.user.UserResponseDto;
import com.kobylchak.bookstore.exception.RegistrationException;

public interface RegistrationService {

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
