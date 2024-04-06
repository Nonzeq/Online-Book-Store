package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.cart.item.CartItemDto;
import com.kobylchak.bookstore.dto.cart.item.CartItemRequestDto;
import com.kobylchak.bookstore.exception.EntityNotFoundException;
import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.model.CartItem;
import com.kobylchak.bookstore.model.ShoppingCart;
import com.kobylchak.bookstore.repository.book.BookRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookRepository.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toModel(
            CartItemRequestDto requestDto,
            @Context BookRepository bookRepository,
            @Context ShoppingCart shoppingCart);

    @AfterMapping
    default void setBook(
            @MappingTarget CartItem cartItem,
            @Context BookRepository bookRepository,
            CartItemRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId())
                            .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        cartItem.setBook(book);

    }

    @AfterMapping
    default void setShoppingCart(
            @MappingTarget CartItem cartItem,
            @Context ShoppingCart shoppingCart) {
        cartItem.setShoppingCart(shoppingCart);
    }
}
