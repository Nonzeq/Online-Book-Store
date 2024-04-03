package com.kobylchak.bookstore.exception;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ResponseExceptionData exceptionData = new ResponseExceptionData();
        exceptionData.setTimestamp(LocalDateTime.now());
        exceptionData.setStatus(HttpStatus.BAD_REQUEST);
        exceptionData.setErrors(getErrorMessages(ex));
        return new ResponseEntity<>(exceptionData, headers, status);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessException(AccessDeniedException exception) {
        ResponseExceptionData exceptionData = new ResponseExceptionData();
        exceptionData.setTimestamp(LocalDateTime.now());
        exceptionData.setStatus(HttpStatus.FORBIDDEN);
        ErrorData errorData = new ErrorData();
        errorData.setMessage(exception.getMessage());
        exceptionData.setErrors(List.of(errorData));
        return new ResponseEntity<>(exceptionData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RegistrationException.class})
    public ResponseEntity<Object> handleRegistrationException(RegistrationException exception) {
        ResponseExceptionData exceptionData = new ResponseExceptionData();
        exceptionData.setTimestamp(LocalDateTime.now());
        exceptionData.setStatus(HttpStatus.BAD_REQUEST);
        ErrorData errorData = new ErrorData();
        errorData.setMessage(exception.getMessage());
        exceptionData.setErrors(List.of(errorData));
        return new ResponseEntity<>(exceptionData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ErrorData> getErrorMessages(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        return allErrors.stream().map(this::getMessage).toList();
    }

    private ErrorData getMessage(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            FieldErrorData errorData = new FieldErrorData();
            String field = ((FieldError) objectError).getField();
            Object rejectedValue = ((FieldError) objectError).getRejectedValue();
            String msg = objectError.getDefaultMessage();

            errorData.setMessage(msg);
            errorData.setField(field);
            errorData.setCause(rejectedValue);
            return errorData;
        }
        ErrorData errorResponseData = new ErrorData();
        errorResponseData.setMessage(objectError.getDefaultMessage());
        return errorResponseData;
    }
}
