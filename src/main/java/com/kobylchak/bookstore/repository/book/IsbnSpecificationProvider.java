package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return BookKeyParameters.ISBN.getKey();
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder)
                -> root.get(getKey()).in(param);
    }
}
