package com.example.animalchipization.api;

import com.example.animalchipization.exception.AlreadyExistException;
import com.example.animalchipization.exception.InaccessibleEntityException;
import com.example.animalchipization.exception.InvalidIdException;
import com.example.animalchipization.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity NotFoundExceptionHandler(NotFoundException notFoundException){
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity ConstraintViolationException(ConstraintViolationException constraintViolationException){
        return new ResponseEntity(constraintViolationException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity IllegalStateException(IllegalStateException illegalStateException){
        return  new ResponseEntity(illegalStateException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity AlreadyExistException(AlreadyExistException alreadyExistException){
        return new ResponseEntity(alreadyExistException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InaccessibleEntityException.class})
    public ResponseEntity InaccessibleEntityException(InaccessibleEntityException inaccessibleEntityException){
        return new ResponseEntity(inaccessibleEntityException.getMessage(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity DataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException){
        return new ResponseEntity(dataIntegrityViolationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity Exception(Exception exception){
        return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
