package com.kobylchak.bookstore.service.order.item;

import com.kobylchak.bookstore.dto.order.item.OrderItemDto;
import com.kobylchak.bookstore.model.CartItem;
import com.kobylchak.bookstore.model.Order;
import java.util.Set;

public interface OrderItemService {
    OrderItemDto getOrderItemById(Long id, Order order);

    Order addOrderItems(Order order, Set<CartItem> cartItems);

    Set<OrderItemDto> getOrderItems(Order order);
}
