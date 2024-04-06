package com.kobylchak.bookstore.service.cart;

import com.kobylchak.bookstore.dto.cart.ShoppingCartDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.mapper.ShoppingCartMapper;
import com.kobylchak.bookstore.model.ShoppingCart;
import com.kobylchak.bookstore.model.User;
import com.kobylchak.bookstore.repository.cart.ShoppingCartRepository;
import com.kobylchak.bookstore.repository.user.UserRepository;
import com.kobylchak.bookstore.service.cart.item.CartItemService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemService cartItemService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartMapper.toDto(getShoppingCartByUser());
    }

    @Override
    @Transactional
    public ShoppingCartDto addItemToShoppingCart(CartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = cartItemService.addItem(requestDto, getShoppingCartByUser());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto removeItemFromShoppingCart(Long id) {
        ShoppingCart shoppingCart = cartItemService.deleteItem(getShoppingCartByUser(), id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateItemInShoppingCart(CartItemRequestUpdateDto requestDto, Long id) {
        ShoppingCart shoppingCart = cartItemService.updateItem(requestDto,
                                                               getShoppingCartByUser(),
                                                               id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart getShoppingCartByUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ShoppingCart> cartByUser = shoppingCartRepository.findByUserEmail(email);
        if (cartByUser.isPresent()) {
            return cartByUser.get();
        }
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userByEmail.orElseThrow(
                () -> new UsernameNotFoundException("User not found")));
        return shoppingCartRepository.save(shoppingCart);
    }
}
