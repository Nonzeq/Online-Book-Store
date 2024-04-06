package com.kobylchak.bookstore.dto.cart.item;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private int quantity;
    private Long bookId;
}
