package com.kobylchak.bookstore.dto.order;

import com.kobylchak.bookstore.model.Order;
import lombok.Data;

@Data
public class OrderStatusUpdateRequestDto {
    private Order.Status status;
}
