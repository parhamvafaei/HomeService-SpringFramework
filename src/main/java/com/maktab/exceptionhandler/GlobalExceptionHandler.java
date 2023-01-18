package com.maktab.exceptionhandler;

import com.maktab.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DeleteExpertException.class)
    public ResponseEntity<Object> DeleteExpertException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = DuplicateServiceException.class)
    public ResponseEntity<Object> DuplicateServiceException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = ExpertAddException.class)
    public ResponseEntity<Object> ExpertAddException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = ExpertConditionException.class)
    public ResponseEntity<Object> ExpertConditionException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = FileReaderException.class)
    public ResponseEntity<Object> FileReaderException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundAnyOrderException.class)
    public ResponseEntity<Object> NotFoundAnyOrderException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundPersonException.class)
    public ResponseEntity<Object> NotFoundPersonException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = OrderStatusConditionException.class)
    public ResponseEntity<Object> OrderStatusConditionException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = PersonSignInException.class)
    public ResponseEntity<Object> PersonSignInException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = SelectOrderException.class)
    public ResponseEntity<Object> SelectOrderException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(value = NotFoundServiceException.class)
    public ResponseEntity<Object> NotFoundServiceException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }



}
