package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.book.BookDto;
import com.kobylchak.bookstore.dto.book.CreateBookRequestDto;
import com.kobylchak.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
