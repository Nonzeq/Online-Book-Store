package com.kobylchak.bookstore.dto;

import com.kobylchak.bookstore.repository.book.BookKeyParameters;
import java.util.Map;

public interface SearchParameters {

    Map<String,String> getParameters();
}
