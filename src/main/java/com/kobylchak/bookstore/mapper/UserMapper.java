package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.dto.user.CreateUserRequestDto;
import com.kobylchak.bookstore.dto.user.UserDto;
import com.kobylchak.bookstore.model.User;

public interface UserMapper {
    UserDto toDto(User user);

    User toModel(CreateUserRequestDto createUserRequestDto);
}
