package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.dto.SearchParameters;
import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.repository.SpecificationBuilder;
import com.kobylchak.bookstore.repository.SpecificationProviderManager;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(SearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        Map<String, String> parameters = searchParameters.getParameters();
        if (parameters == null) {
            return spec;
        }
        for (Map.Entry<String, String> bookKeyParameters : parameters.entrySet()) {
            String parameter = bookKeyParameters.getValue();
            if (parameter != null && !parameter.isEmpty()) {
                spec = spec.and(
                        bookSpecificationProviderManager
                                .getSpecificationProvider(bookKeyParameters.getKey())
                                .getSpecification(bookKeyParameters.getValue())
                );
            }
        }
        return spec;
    }
}
