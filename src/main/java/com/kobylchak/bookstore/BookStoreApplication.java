package com.kobylchak.bookstore;

import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.service.BookService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class BookStoreApplication {

    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunnerBean() {
        return args -> {
            Book book = new Book();
            book.setAuthor("test");
            book.setPrice(BigDecimal.valueOf(200));
            book.setIsbn("2353254");
            book.setTitle("1984");
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
