package com.nttdata.dockerized.postgresql.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConf {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handle(ConstraintViolationException e){
        Response responseError = new Response();

        e.getConstraintViolations().forEach(
                r-> {
                    Error error = new Error();
                    error.setCode(HttpStatus.BAD_REQUEST.name());
                    error.setMessage(r.getMessage());
                    responseError.addError(error);
                }
        );
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handle(ResourceNotFoundException e){
        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.name());
        error.setMessage(e.getMessage());
        return  new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error> handle(BadRequestException e) {
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.name());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Error> handle(InternalServerErrorException e) {
        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
