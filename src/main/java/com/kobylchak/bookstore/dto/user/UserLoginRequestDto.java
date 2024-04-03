package com.kobylchak.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @Email
    private String email;
    @Size(min = 8)
    private String password;
}
