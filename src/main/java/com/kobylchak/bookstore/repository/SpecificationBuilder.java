package com.kobylchak.bookstore.repository;

import com.kobylchak.bookstore.dto.SearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(SearchParameters searchParameters);
}
