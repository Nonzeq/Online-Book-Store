package com.kobylchak.bookstore.service;

import com.kobylchak.bookstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
