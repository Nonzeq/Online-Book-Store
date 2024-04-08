package com.kobylchak.bookstore.service.order;

import com.kobylchak.bookstore.dto.order.CreateOrderRequestDto;
import com.kobylchak.bookstore.dto.order.OrderDto;
import com.kobylchak.bookstore.dto.order.OrderStatusUpdateRequestDto;
import com.kobylchak.bookstore.dto.order.item.OrderItemDto;
import com.kobylchak.bookstore.exception.EmptyShoppingCartException;
import com.kobylchak.bookstore.exception.EntityNotFoundException;
import com.kobylchak.bookstore.mapper.OrderMapper;
import com.kobylchak.bookstore.model.Order;
import com.kobylchak.bookstore.model.ShoppingCart;
import com.kobylchak.bookstore.model.User;
import com.kobylchak.bookstore.repository.order.OrderRepository;
import com.kobylchak.bookstore.service.cart.ShoppingCartService;
import com.kobylchak.bookstore.service.order.item.OrderItemService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShoppingCartService shoppingCartService;
    private final OrderItemService orderItemService;

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUser(user);
        if (!shoppingCart.getCartItems().isEmpty()) {
            Order order = orderMapper.toModel(requestDto, shoppingCart.getCartItems(), user);
            order = orderRepository.save(order);
            orderItemService.addOrderItems(order, shoppingCart.getCartItems());
            shoppingCartService.deleteShoppingCart(shoppingCart.getId());
            return orderMapper.toDto(orderRepository.save(order));
        }
        throw new EmptyShoppingCartException("Shopping cart is empty!");
    }

    @Override
    public List<OrderDto> getAllOrders(User user, Pageable pageable) {
        return orderMapper.toDtos(orderRepository.findAllByUserId(user.getId(), pageable)
                                          .getContent());
    }

    @Override
    public void updateStatus(OrderStatusUpdateRequestDto requestDto, Long id) {
        orderRepository.updateOrderStatusById(id, requestDto.getStatus());
    }

    @Override
    public Set<OrderItemDto> getAllOrderItemsByOrderId(Long orderId, User user, Pageable pageable) {
        Optional<Order> order = orderRepository.findByIdAndUserId(orderId, user.getId());
        if (order.isPresent()) {
            return orderItemService.getOrderItems(order.get());
        }
        return Set.of();
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId, User user) {
        Optional<Order> orderById = orderRepository.findByIdAndUserId(orderId, user.getId());
        if (orderById.isPresent()) {
            return orderItemService.getOrderItemById(itemId, orderById.get());
        }
        throw new EntityNotFoundException("Order not found!");
    }
}
