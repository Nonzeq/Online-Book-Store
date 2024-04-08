package com.kobylchak.bookstore.service.order;

import com.kobylchak.bookstore.dto.order.CreateOrderRequestDto;
import com.kobylchak.bookstore.dto.order.OrderDto;
import com.kobylchak.bookstore.dto.order.OrderStatusUpdateRequestDto;
import com.kobylchak.bookstore.dto.order.item.OrderItemDto;
import com.kobylchak.bookstore.model.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequestDto requestDto, User user);

    List<OrderDto> getAllOrders(User user, Pageable pageable);

    void updateStatus(OrderStatusUpdateRequestDto requestDto, Long orderId);

    Set<OrderItemDto> getAllOrderItemsByOrderId(Long orderId, User user, Pageable pageable);

    OrderItemDto getOrderItemById(Long orderId, Long itemId, User user);
}
