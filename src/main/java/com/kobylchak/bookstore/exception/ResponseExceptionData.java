package com.kobylchak.bookstore.exception;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseExceptionData {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private List<ErrorData> errors;


}
