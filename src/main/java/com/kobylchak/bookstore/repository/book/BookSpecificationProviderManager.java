package com.kobylchak.bookstore.repository.book;

import com.kobylchak.bookstore.model.Book;
import com.kobylchak.bookstore.repository.SpecificationProvider;
import com.kobylchak.bookstore.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Cannot find correct specifitation provider"
                               + "for key: " + key));
    }
}
