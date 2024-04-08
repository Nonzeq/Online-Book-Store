package com.kobylchak.bookstore.service.cart.item;

import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.model.ShoppingCart;

public interface CartItemService {
    ShoppingCart addItem(CartItemRequestDto requestDto, ShoppingCart shoppingCart);

    ShoppingCart updateItem(
            CartItemRequestUpdateDto requestDto,
            ShoppingCart shoppingCart,
            Long id);

    ShoppingCart deleteItem(ShoppingCart shoppingCart, Long id);
}
