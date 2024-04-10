package com.kobylchak.bookstore.controller;

import com.kobylchak.bookstore.dto.order.CreateOrderRequestDto;
import com.kobylchak.bookstore.dto.order.OrderDto;
import com.kobylchak.bookstore.dto.order.OrderStatusUpdateRequestDto;
import com.kobylchak.bookstore.dto.order.item.OrderItemDto;
import com.kobylchak.bookstore.model.User;
import com.kobylchak.bookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing user's orders")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get users's orders",
               description = "API for getting all user's orders")
    public List<OrderDto> getOrders(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrders(user, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Patch order status",
               description = "API for patching order status by id")
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid OrderStatusUpdateRequestDto requestDto) {
        orderService.updateStatus(requestDto, id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Create order",
               description = "API for creating order")
    public OrderDto createOrder(
            Authentication authentication,
            @RequestBody @Valid CreateOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(requestDto, user);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get user's order items",
               description = "API for getting user's order items")
    public Set<OrderItemDto> getOrderItems(
            Authentication authentication,
            @PathVariable Long orderId,
            Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrderItemsByOrderId(orderId, user, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get order item from user's order",
               description = "API for getting order item from user's order by order id and item id")
    public OrderItemDto getOrderItem(
            Authentication authentication,
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemById(orderId, itemId, user);
    }
}
