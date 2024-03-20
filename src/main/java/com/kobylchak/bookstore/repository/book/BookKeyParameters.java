package com.kobylchak.bookstore.repository.book;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BookKeyParameters {
    AUTHOR("author"),
    TITLE("title"),
    PRICE("price"),
    ISBN("isbn");
    private final String key;

    BookKeyParameters(String key) {
        this.key = key;
    }

}
