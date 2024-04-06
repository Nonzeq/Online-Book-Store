package com.kobylchak.bookstore.service.cart;

import com.kobylchak.bookstore.dto.cart.ShoppingCartDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addItemToShoppingCart(CartItemRequestDto requestDto);

    ShoppingCartDto removeItemFromShoppingCart(Long id);

    ShoppingCartDto updateItemInShoppingCart(CartItemRequestUpdateDto requestDto, Long id);

    ShoppingCart getShoppingCartByUser();
}
