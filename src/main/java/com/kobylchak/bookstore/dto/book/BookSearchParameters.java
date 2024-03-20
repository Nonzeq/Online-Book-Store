package com.kobylchak.bookstore.dto.book;

import com.kobylchak.bookstore.dto.SearchParameters;
import com.kobylchak.bookstore.repository.book.BookKeyParameters;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class BookSearchParameters implements SearchParameters {
    private String title;
    private String author;
    private String isbn;
    private BigDecimal upperPriceThreshold;
    private BigDecimal lowerPriceThreshold;

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>();
        if (title != null) {
            parameters.put(BookKeyParameters.TITLE.getKey(), title);
        }

        if (author != null) {
            parameters.put(BookKeyParameters.AUTHOR.getKey(), author);
        }

        if (isbn != null) {
            parameters.put(BookKeyParameters.ISBN.getKey(), isbn);
        }
        return parameters;
    }
}
