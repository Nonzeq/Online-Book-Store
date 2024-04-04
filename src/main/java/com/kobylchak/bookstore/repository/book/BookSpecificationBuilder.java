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
        Specification<Book> spec = Specification.where(getSpecWithCategoryJoin());
        Map<String, String> parameters = searchParameters.getParameters();
        for (Map.Entry<String, String> bookKeyValueParameter : parameters.entrySet()) {
            String keyParameter = bookKeyValueParameter.getKey();
            String valueParameter = bookKeyValueParameter.getValue();
            if (valueParameter != null && !valueParameter.isEmpty()) {
                spec = spec.and(
                        bookSpecificationProviderManager
                                .getSpecificationProvider(keyParameter)
                                .getSpecification(valueParameter)
                );
            }
        }
        return spec;
    }

    private Specification<Book> getSpecWithCategoryJoin() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("categories");
            return null;
        };
    }
}
