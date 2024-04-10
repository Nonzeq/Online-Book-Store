package com.kobylchak.bookstore.controller;

import com.kobylchak.bookstore.dto.cart.ShoppingCartDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.model.User;
import com.kobylchak.bookstore.service.cart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing Shopping carts")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get shopping cart",
               description = "API for getting user's shopping cart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCart(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Add item to shopping cart",
               description = "API for adding item to user's shopping cart")
    public ShoppingCartDto addItem(@RequestBody CartItemRequestDto requestDto,
                                   Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addItemToShoppingCart(requestDto, user);
    }

    @PutMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Update item in shopping cart",
               description = "API for updating item in user's shopping cart")
    public ShoppingCartDto updateItem(@RequestBody CartItemRequestUpdateDto requestDto,
                                      @PathVariable Long id,
                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateItemInShoppingCart(requestDto, id, user);
    }

    @DeleteMapping("/cart-items/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Delete item from shopping cart",
               description = "API for deleting item from user's shopping cart")
    public ShoppingCartDto deleteItem(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.removeItemFromShoppingCart(id, user);
    }
}
