package com.kobylchak.bookstore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class FieldErrorData extends ErrorData {
    private String field;
    private Object cause;
}


