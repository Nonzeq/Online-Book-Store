package com.kobylchak.bookstore.repository;

import com.kobylchak.bookstore.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookById(Long id);
}
