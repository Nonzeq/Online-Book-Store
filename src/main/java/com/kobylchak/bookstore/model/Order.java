package com.kobylchak.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "orders")
@Getter
@Setter
@SQLDelete(sql = "UPDATE orders set is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=FALSE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false, name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    @Column(name = "order_items")
    private Set<OrderItem> orderItems;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;

    public enum Status {
        COMPLETED,
        PENDING,
        DELIVERED,
        CREATED
    }
}
