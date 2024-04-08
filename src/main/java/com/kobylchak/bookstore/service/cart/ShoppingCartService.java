package com.kobylchak.bookstore.service.cart;

import com.kobylchak.bookstore.dto.cart.ShoppingCartDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.model.ShoppingCart;
import com.kobylchak.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCart getShoppingCartByUser(User user);

    ShoppingCartDto getShoppingCart(User user);

    ShoppingCartDto addItemToShoppingCart(CartItemRequestDto requestDto, User user);

    ShoppingCartDto removeItemFromShoppingCart(Long id, User user);

    ShoppingCartDto updateItemInShoppingCart(
            CartItemRequestUpdateDto requestDto, Long id,
            User user);

    void deleteShoppingCart(Long id);
}
