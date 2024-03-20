package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.repository.SpecificationProvider;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UpperPriceLimitSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return BookKeyParameters.UPPER_PRICE_LIMIT.getKey();
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
                        root.get(BookKeyParameters.PRICE.getKey()),
                        BigDecimal.valueOf(Long.parseLong(param))
        );
    }
}
