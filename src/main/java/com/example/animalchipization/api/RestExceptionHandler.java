package com.example.animalchipization.api;

import com.example.animalchipization.exception.InvalidIdException;
import com.example.animalchipization.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity NotFoundExceptionHandler(NotFoundException notFoundException){
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidIdException.class})
    public ResponseEntity InvalidIdExceptionHandler(InvalidIdException invalidIdException){
        return new ResponseEntity(invalidIdException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
