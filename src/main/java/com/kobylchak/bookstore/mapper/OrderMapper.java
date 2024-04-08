package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.order.CreateOrderRequestDto;
import com.kobylchak.bookstore.dto.order.OrderDto;
import com.kobylchak.bookstore.dto.order.OrderStatusUpdateRequestDto;
import com.kobylchak.bookstore.model.CartItem;
import com.kobylchak.bookstore.model.Order;
import com.kobylchak.bookstore.model.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    Order toModel(
            CreateOrderRequestDto requestDto,
            @Context Set<CartItem> cartItems,
            @Context User user);

    Order toModel(OrderStatusUpdateRequestDto requestDto);

    @AfterMapping
    default void setUser(@MappingTarget Order order, @Context User user) {
        order.setUser(user);
    }

    @AfterMapping
    default void setTotal(@MappingTarget Order order, @Context Set<CartItem> cartItems) {
        cartItems.stream()
                         .map(cartItem -> cartItem.getBook().getPrice().multiply(
                                 BigDecimal.valueOf(cartItem.getQuantity())))
                                 .reduce(BigDecimal::add).ifPresent(order::setTotal);
    }

    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    List<OrderDto> toDtos(List<Order> orders);
}
