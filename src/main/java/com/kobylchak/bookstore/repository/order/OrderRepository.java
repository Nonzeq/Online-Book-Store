package com.kobylchak.bookstore.repository.order;

import com.kobylchak.bookstore.model.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o left join fetch o.user u "
               + "left join fetch o.orderItems oi "
               + "left join fetch oi.book "
               + "where u.id = :id")
    Page<Order> findAllByUserId(Long id, Pageable pageable);

    @Query("from Order o "
               + "left join fetch o.user u "
               + "left join fetch o.orderItems oi "
               + "left join fetch oi.book "
               + "where o.id = :id and u.id = :userId")
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Transactional
    @Query("update Order o set o.status = :status where o.id = :id")
    void updateOrderStatusById(Long id, Order.Status status);
}
