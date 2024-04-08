package com.kobylchak.bookstore.dto.order;

import lombok.Data;

@Data
public class CreateOrderRequestDto {
    private String shippingAddress;
}
