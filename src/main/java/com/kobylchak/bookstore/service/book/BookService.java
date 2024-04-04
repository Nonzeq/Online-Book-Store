package com.kobylchak.bookstore.service.book;

import com.kobylchak.bookstore.dto.book.BookDto;
import com.kobylchak.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.kobylchak.bookstore.dto.book.BookSearchParameters;
import com.kobylchak.bookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto bookRequestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters, Pageable pageable);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);

}
