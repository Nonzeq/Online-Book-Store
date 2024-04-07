package com.kobylchak.bookstore.service.cart;

import com.kobylchak.bookstore.dto.cart.ShoppingCartDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestUpdateDto;
import com.kobylchak.bookstore.mapper.ShoppingCartMapper;
import com.kobylchak.bookstore.model.ShoppingCart;
import com.kobylchak.bookstore.model.User;
import com.kobylchak.bookstore.repository.cart.ShoppingCartRepository;
import com.kobylchak.bookstore.service.cart.item.CartItemService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCartDto getShoppingCart(User user) {
        return shoppingCartMapper.toDto(getShoppingCartByUser(user));
    }

    @Override
    public ShoppingCartDto addItemToShoppingCart(CartItemRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = cartItemService
                                            .addItem(requestDto, getShoppingCartByUser(user));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto removeItemFromShoppingCart(Long id, User user) {
        ShoppingCart shoppingCart = cartItemService.deleteItem(getShoppingCartByUser(user), id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateItemInShoppingCart(CartItemRequestUpdateDto requestDto, Long id,
                                                    User user) {
        ShoppingCart shoppingCart = cartItemService.updateItem(requestDto,
                                                               getShoppingCartByUser(user),
                                                               id);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    private ShoppingCart getShoppingCartByUser(User user) {
        Optional<ShoppingCart> cartByUser = shoppingCartRepository.findByUserEmail(user.getEmail());
        if (cartByUser.isPresent()) {
            return cartByUser.get();
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }
}
