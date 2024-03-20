package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.model.Book;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;

public class PriceSpecificationProvider {
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get("author").in(Arrays.stream(params).toArray());
    }
}
