package com.kobylchak.bookstore.dto.cart;

import com.kobylchak.bookstore.dto.cart.item.CartItemDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
