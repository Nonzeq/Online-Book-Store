package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public Specification<Book> getSpecification(String params) {
        return (root, query, criteriaBuilder) -> root.get(getKey()).in(params);
    }

    @Override
    public String getKey() {
        return BookKeyParameters.AUTHOR.getKey();
    }

}
