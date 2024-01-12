package com.den.exceptions;

import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(NotFoundError.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<?> handlerNotFoundException(NotFoundError ex, WebRequest req) {
    ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BabRequestError.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handlerBabRequestError(BabRequestError ex, WebRequest req) {
    ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DuplicateRecordError.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handlerDuplicateRecordException(DuplicateRecordError ex, WebRequest req) {
    ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleBindException(BindException e) {
    String errorMessage = "Bad Request: ";
    if (e.getBindingResult().hasErrors())
      errorMessage += e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = "Invalid request format: ";
    ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST,
        errorMessage + ex.getAllErrors().get(0).getDefaultMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ParseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleParseException(ParseException ex) {
    String errorMessage = "Invalid date format: ";
    ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST,
        errorMessage + ex.getSimpleMessage());
    return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
  }

  // Xử lý tất cả các exception chưa được khai báo
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerException(Exception ex, WebRequest req) {
    ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
