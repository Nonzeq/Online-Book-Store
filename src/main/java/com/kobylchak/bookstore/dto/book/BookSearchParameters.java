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
        parameters.put(BookKeyParameters.TITLE.getKey(), title);
        parameters.put(BookKeyParameters.AUTHOR.getKey(), author);
        parameters.put(BookKeyParameters.ISBN.getKey(), isbn);
        parameters.put(BookKeyParameters.UPPER_PRICE_LIMIT.getKey(), upperPriceLimit);
        parameters.put(BookKeyParameters.LOWER_PRICE_LIMIT.getKey(), lowerPriceLimit);
        return parameters;
    }
}
