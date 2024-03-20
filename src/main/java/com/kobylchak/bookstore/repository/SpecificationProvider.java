package com.kobylchak.bookstore.repository;

import com.kobylchak.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();
    Specification<T> getSpecification(String param);
}
