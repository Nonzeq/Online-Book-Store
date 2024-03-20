package com.kobylchak.bookstore.dto.book;

import com.kobylchak.bookstore.dto.SearchParameters;
import com.kobylchak.bookstore.repository.book.BookKeyParameters;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class BookSearchParameters implements SearchParameters {
    private String title;
    private String author;
    private String isbn;
    private String upperPriceLimit;
    private String lowerPriceLimit;

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
        if (upperPriceLimit != null) {
            parameters.put(BookKeyParameters.UPPER_PRICE_LIMIT.getKey(), upperPriceLimit);
        }
        if (lowerPriceLimit != null) {
            parameters.put(BookKeyParameters.LOWER_PRICE_LIMIT.getKey(), lowerPriceLimit);
        }
        return parameters;
    }
}
