package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.model.Book;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAllByCategoryId_FourActualCategories_ReturnFourBooks() {
        Long categoryId = 1L;
        int expected = 4;
        List<Book> booksByCategoryId = bookRepository.findAllByCategoryId(categoryId,
                                                                          Pageable.unpaged())
                                               .getContent();
        Assertions.assertEquals(expected, booksByCategoryId.size());
    }

}
