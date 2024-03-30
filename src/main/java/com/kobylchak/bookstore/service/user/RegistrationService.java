package com.kobylchak.bookstore.service.user;

import com.kobylchak.bookstore.dto.user.UserRegistrationRequestDto;
import com.kobylchak.bookstore.dto.user.UserResponseDto;
import com.kobylchak.bookstore.exception.RegistrationException;
import java.util.List;

public interface RegistrationService {

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    List<UserResponseDto> getAll();
}
