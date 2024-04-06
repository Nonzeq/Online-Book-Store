package com.kobylchak.bookstore.service.cart.item;

import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.exception.EntityNotFoundException;
import com.kobylchak.bookstore.mapper.CartItemMapper;
import com.kobylchak.bookstore.model.CartItem;
import com.kobylchak.bookstore.model.ShoppingCart;
import com.kobylchak.bookstore.repository.book.BookRepository;
import com.kobylchak.bookstore.repository.cart.item.CartItemRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ShoppingCart addItem(CartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto, bookRepository, shoppingCart);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCart;
    }

    @Override
    public ShoppingCart updateItem(
            CartItemRequestUpdateDto requestDto,
            ShoppingCart shoppingCart,
            Long id) {
        Optional<CartItem> updatedItem = shoppingCart.getCartItems()
                                                 .stream()
                                                 .filter(cartItem -> Objects.equals(
                                                         cartItem.getId(),
                                                         id))
                                                 .findFirst();
        if (updatedItem.isPresent()) {
            CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("CartItem not found"));
            cartItem.setQuantity(requestDto.getQuantity());
            shoppingCart.getCartItems().add(cartItemRepository.save(cartItem));
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart deleteItem(
            ShoppingCart shoppingCart,
            Long id) {
        Optional<CartItem> deletedItem = shoppingCart.getCartItems().stream()
                                                 .filter(cartItem -> Objects.equals(
                                                         cartItem.getId(), id))
                                                 .findFirst();
        deletedItem.ifPresent(cartItemRepository::delete);
        shoppingCart.setCartItems(cartItemRepository.findByShoppingCartId(shoppingCart.getId()));
        return shoppingCart;
    }
}
