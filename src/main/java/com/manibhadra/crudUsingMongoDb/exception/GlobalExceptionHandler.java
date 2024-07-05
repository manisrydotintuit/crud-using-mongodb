package com.manibhadra.crudUsingMongoDb.exception;

import com.manibhadra.crudUsingMongoDb.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<?> response = new ApiResponse<>(null, false, HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ApiResponse<?>> handleDatabaseOperationException(DatabaseOperationException ex) {
        ApiResponse<?> response = new ApiResponse<>(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "Database operation error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiResponse<?> response = new ApiResponse<>(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "Unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
