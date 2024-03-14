package com.kobylchak.bookstore.service;

import com.kobylchak.bookstore.dto.BookDto;
import com.kobylchak.bookstore.dto.CreateBookRequestDto;
import com.kobylchak.bookstore.model.Book;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto getBookById(Long id);
}
