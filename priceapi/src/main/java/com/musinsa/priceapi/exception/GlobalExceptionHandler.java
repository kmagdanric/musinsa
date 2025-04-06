package com.musinsa.priceapi.exception;

import com.musinsa.priceapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProductNotFoundException(
      ProductNotFoundException ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorResponse.of(
                500,
                ex.getMessage(),
                "Data Not Found",
                request.getDescription(false).replace("uri=", "")));
  }

  @ExceptionHandler(NoCompleteBrandException.class)
  public ResponseEntity<ErrorResponse> handleNoCompleteBrandException(
      NoCompleteBrandException ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorResponse.of(
                500,
                ex.getMessage(),
                "Data Not Found",
                request.getDescription(false).replace("uri=", "")));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ErrorResponse.of(
                500,
                "An unexpected error occurred",
                "Internal Server Error",
                request.getDescription(false).replace("uri=", "")));
  }
}
