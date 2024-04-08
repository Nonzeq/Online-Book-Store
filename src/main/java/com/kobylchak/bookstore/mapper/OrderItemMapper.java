package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.order.item.OrderItemDto;
import com.kobylchak.bookstore.model.CartItem;
import com.kobylchak.bookstore.model.Order;
import com.kobylchak.bookstore.model.OrderItem;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItem toModel(OrderItemDto orderItemDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "book.price")
    OrderItem toModel(CartItem cartItem, @Context Order order);

    @AfterMapping
    default void setOrder(@Context Order order, @MappingTarget OrderItem orderItem) {
        orderItem.setOrder(order);
    }

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    Set<OrderItemDto> toDtos(Set<OrderItem> orderItem);

    Set<OrderItem> toOrderItems(Set<CartItem> cartItems, @Context Order order);
}
