package com.kobylchak.bookstore.repository.cart;

import com.kobylchak.bookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("from ShoppingCart sh "
               + "left join fetch sh.user u "
               + "left join fetch sh.cartItems ca "
               + "left join fetch ca.book b "
               + "left join fetch ca.shoppingCart "
               + "left join fetch b.categories "
               + "where u.email = :email")
    Optional<ShoppingCart> findByUserEmail(String email);
}
