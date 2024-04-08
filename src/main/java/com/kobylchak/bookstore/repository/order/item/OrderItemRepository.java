package com.kobylchak.bookstore.repository.order.item;

import com.kobylchak.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
