package com.kobylchak.bookstore.repository.cart.item;

import com.kobylchak.bookstore.model.CartItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("from CartItem ci join fetch ci.book join fetch ci.shoppingCart shc "
            + "where shc.id = :id")
    Set<CartItem> findAllByShoppingCartId(Long id);

    @Query("from CartItem ci left join fetch ci.shoppingCart left join fetch ci.book "
            + "where ci.id = :id")
    Optional<CartItem> findByIdWithBook(Long id);
}
