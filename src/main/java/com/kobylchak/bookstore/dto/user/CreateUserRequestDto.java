package com.kobylchak.bookstore.dto.user;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    private String email;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}
