package com.kobylchak.bookstore.repository.user;

import com.kobylchak.bookstore.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u join fetch u.roles where u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("from User u join fetch u.roles")
    List<User> findAllUsers();
}
