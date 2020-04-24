package com.example.demo.exception.exceptionHandler;

import com.example.demo.exception.OutOfBudgetException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class MyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        System.out.println("UserNotFound Handler");
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<ExceptionResponse> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        System.out.println("UserAlreadyExist Handler");
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfBudgetException.class)
    public final ResponseEntity<ExceptionResponse> handleOutOfBudgetException(OutOfBudgetException ex) {
        System.out.println("OutOfBudgetException Handler");
        return createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> createErrorResponseEntity(Exception ex, HttpStatus httpStatus){
        return new ResponseEntity<>(new ExceptionResponse(new Date(), ex.getMessage()), httpStatus);
    }

}
