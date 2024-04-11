package com.kobylchak.bookstore.service.book;

import static org.mockito.Mockito.when;

import com.kobylchak.bookstore.dto.book.BookDto;
import com.kobylchak.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.kobylchak.bookstore.dto.book.CreateBookRequestDto;
import com.kobylchak.bookstore.exception.EntityNotFoundException;
import com.kobylchak.bookstore.mapper.BookMapper;
import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.model.Category;
import com.kobylchak.bookstore.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;
    private Book validBookWihoutId;
    private BookDto validBookDtoWihoutId;
    private CreateBookRequestDto validCreateBookRequestDto;

    @BeforeEach
    void setUp() {
        final Book book = new Book();
        book.setTitle("Test title");
        book.setAuthor("Test author");
        book.setIsbn("testisbn");
        book.setPrice(BigDecimal.valueOf(300));
        book.setCategories(Set.of(new Category(1L)));
        this.validBookWihoutId = book;

        final CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle(book.getTitle());
        createBookRequestDto.setAuthor(book.getAuthor());
        createBookRequestDto.setIsbn(book.getIsbn());
        createBookRequestDto.setPrice(book.getPrice());
        this.validCreateBookRequestDto = createBookRequestDto;

        final BookDto bookDto = new BookDto();
        book.setTitle(book.getTitle());
        book.setAuthor(book.getAuthor());
        book.setIsbn(book.getIsbn());
        book.setPrice(book.getPrice());
        book.setCategories(Set.of(new Category(1L)));
        this.validBookDtoWihoutId = bookDto;

    }

    @Test
    void saveBookAndFindAllBooks_WithValidParams_ShouldReturnValidEntities() {
        Long bookId = 1L;
        final Book book = validBookWihoutId;
        CreateBookRequestDto requestDto = validCreateBookRequestDto;
        final BookDto bookDto = validBookDtoWihoutId;
        book.setId(bookId);

        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        BookDto expected = validBookDtoWihoutId;
        expected.setId(bookId);
        when(bookMapper.toDto(book)).thenReturn(expected);
        BookDto actual = bookService.save(validCreateBookRequestDto);

        Assertions.assertEquals(expected, actual);

        PageImpl<Book> books = new PageImpl<>(List.of(book));
        when(bookRepository.findAllWithCategories(Pageable.unpaged()))
                .thenReturn(books);
        bookDto.setId(bookId);
        List<BookDto> bookDtos = List.of(bookDto);
        when(bookMapper.toDtos(books.getContent())).thenReturn(bookDtos);
        List<BookDto> allBooks = bookService.findAll(Pageable.unpaged());
        Assertions.assertFalse(allBooks.isEmpty());
        Assertions.assertIterableEquals(allBooks, bookDtos);

    }

    @Test
    public void getBookById_WithValidBookId_ShouldReturnValidBookDto() {
        Long bookId = 1L;
        Book book = validBookWihoutId;
        book.setId(bookId);
        BookDto expected = new BookDto();
        expected.setId(bookId);
        expected.setTitle(book.getTitle());
        expected.setAuthor(book.getAuthor());
        expected.setIsbn(book.getIsbn());
        expected.setPrice(book.getPrice());
        expected.setCategoryIds(Set.of(1L));

        when(bookRepository.findBookByIdWithCategories(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.getBookById(1L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getBookById_WithInValidBookId_ShouldThrowEntityNotFoundException() {
        Long invalidBookId = 22L;

        when(bookRepository.findBookByIdWithCategories(invalidBookId)).thenReturn(Optional.empty());

        String expected = Assertions.assertThrows(EntityNotFoundException.class,
                                                  () -> bookService.getBookById(invalidBookId))
                                  .getMessage();
        String actual = "Book by id: " + invalidBookId + "not found";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateBookById_WithValidBookId_ShouldReturnValidUpdateBookDto() {
        final Long bookId = 1L;
        CreateBookRequestDto createBookRequestDto = validCreateBookRequestDto;

        Book book = new Book();
        book.setTitle(createBookRequestDto.getTitle());
        book.setAuthor(createBookRequestDto.getAuthor());
        book.setIsbn(createBookRequestDto.getIsbn());
        book.setPrice(createBookRequestDto.getPrice());

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle(createBookRequestDto.getTitle());
        expectedDto.setAuthor(createBookRequestDto.getAuthor());
        expectedDto.setIsbn(createBookRequestDto.getIsbn());
        expectedDto.setPrice(createBookRequestDto.getPrice());

        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.updateBookById(bookId, createBookRequestDto);
        Assertions.assertEquals(expectedDto, actualDto);

    }

    @Test
    public void updateBookById_WithInvalidBookId_ShouldThrowEntityNotFoundException() {
        Long invalidBookIt = 22L;
        CreateBookRequestDto createBookRequestDto = validCreateBookRequestDto;
        when(bookRepository.existsById(invalidBookIt)).thenReturn(false);

        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                                                             () -> bookService.updateBookById(
                                                                     invalidBookIt,
                                                                     createBookRequestDto));
        String actual = ex.getMessage();
        String expected = "Book by id: " + invalidBookIt + " not found";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getBooksByCategoryId_WithValidParams_ShouldReturnBookDTosWithoutCategoriesIds() {
        Long categoryId = 1L;
        Pageable pageable = Pageable.unpaged();
        Page<Book> bookDtos = getPageBookDtos();
        Page<BookDtoWithoutCategoryIds> bookPageDtosWihoutCategoryIds =
                getPageBookPageDtosWihoutCategoryIds();
        when(bookRepository.findAllByCategoryId(categoryId, pageable))
                .thenReturn(bookDtos);
        when(bookMapper.toWithoutCategoryIdsDto(bookDtos.getContent()))
                .thenReturn(bookPageDtosWihoutCategoryIds.getContent());
        List<BookDtoWithoutCategoryIds> actual = bookService.getBooksByCategoryId(
                categoryId, pageable);
        Assertions.assertIterableEquals(bookPageDtosWihoutCategoryIds, actual);

    }

    private Page<BookDtoWithoutCategoryIds> getPageBookPageDtosWihoutCategoryIds() {
        final BookDtoWithoutCategoryIds book1 = new BookDtoWithoutCategoryIds();
        book1.setId(1L);
        book1.setTitle("test1");
        book1.setAuthor("test1");
        book1.setIsbn("test1");
        final BookDtoWithoutCategoryIds book2 = new BookDtoWithoutCategoryIds();
        book1.setId(2L);
        book1.setTitle("test2");
        book1.setAuthor("test2");
        book1.setIsbn("test2");
        return new PageImpl<>(List.of(book2, book1));
    }

    private Page<Book> getPageBookDtos() {
        final Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("test1");
        book1.setAuthor("test1");
        book1.setIsbn("test1");
        final Book book2 = new Book();
        book1.setId(2L);
        book1.setTitle("test2");
        book1.setAuthor("test2");
        book1.setIsbn("test2");
        return new PageImpl<>(List.of(book2, book1));
    }
}
