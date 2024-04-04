package com.kobylchak.bookstore.mapper;

import com.kobylchak.bookstore.config.MapperConfig;
import com.kobylchak.bookstore.dto.book.BookDto;
import com.kobylchak.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.kobylchak.bookstore.dto.book.CreateBookRequestDto;
import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.model.Category;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    List<BookDtoWithoutCategoryIds> toWithoutCategoryIdsDto(List<Book> books);

    List<BookDto> toDtos(List<Book> books);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                                        .map(Category::getId)
                                        .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }

    BookDtoWithoutCategoryIds toWithoutCategoryIdDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
