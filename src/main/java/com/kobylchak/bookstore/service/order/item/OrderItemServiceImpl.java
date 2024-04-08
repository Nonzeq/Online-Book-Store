package com.kobylchak.bookstore.service.order.item;

import com.kobylchak.bookstore.dto.order.item.OrderItemDto;
import com.kobylchak.bookstore.exception.EntityNotFoundException;
import com.kobylchak.bookstore.mapper.OrderItemMapper;
import com.kobylchak.bookstore.model.CartItem;
import com.kobylchak.bookstore.model.Order;
import com.kobylchak.bookstore.model.OrderItem;
import com.kobylchak.bookstore.repository.order.item.OrderItemRepository;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemDto getOrderItemById(Long id, Order order) {
        OrderItem item = order.getOrderItems().stream()
                                 .filter(orderItem -> Objects.equals(order.getId(), id))
                                 .findFirst()
                                 .orElseThrow(
                                         () -> new EntityNotFoundException("OrderItem with id "
                                                                           + id
                                                                           + " not found"));
        return orderItemMapper.toDto(item);
    }

    @Override
    public Order addOrderItems(Order order, Set<CartItem> cartItems) {
        Set<OrderItem> orderItems = orderItemMapper.toOrderItems(cartItems, order);
        order.setOrderItems(new HashSet<>(orderItemRepository.saveAll(orderItems)));
        return order;
    }

    @Override
    public Set<OrderItemDto> getOrderItems(Order order) {
        return orderItemMapper.toDtos(order.getOrderItems());
    }
}
