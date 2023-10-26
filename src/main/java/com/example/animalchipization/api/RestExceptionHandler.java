package com.example.animalchipization.api;

import com.example.animalchipization.exception.InvalidIdException;
import com.example.animalchipization.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity NotFoundExceptionHandler(NotFoundException notFoundException){
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity ConstraintViolationException(ConstraintViolationException constraintViolationException){
        return new ResponseEntity(constraintViolationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
