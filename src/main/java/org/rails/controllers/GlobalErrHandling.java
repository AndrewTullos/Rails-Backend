package org.rails.controllers;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalErrHandling {

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<String> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatchException(TypeMismatchException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<String> handleBadSqlGrammar(SQLSyntaxErrorException ex) {
        return new ResponseEntity<String>("Bad SQL Grammar, check your code buddy", HttpStatus.INTERNAL_SERVER_ERROR);
    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return new ResponseEntity<String>("Something went wrong on our end :(", HttpStatus.I_AM_A_TEAPOT);
//    }
}
