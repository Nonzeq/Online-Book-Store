package com.kobylchak.bookstore.repository.cart.item;

import com.kobylchak.bookstore.model.CartItem;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("from CartItem ci join fetch ci.book join fetch ci.shoppingCart shc "
            + "where shc.id = :id")
    Set<CartItem> findByShoppingCartId(Long id);
}
