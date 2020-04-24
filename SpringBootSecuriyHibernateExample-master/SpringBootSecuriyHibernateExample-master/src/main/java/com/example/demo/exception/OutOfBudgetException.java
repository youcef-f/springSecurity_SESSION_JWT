package com.example.demo.exception;

public class OutOfBudgetException extends RuntimeException {

    public OutOfBudgetException() {
        super();
    }

    public OutOfBudgetException(String message) {
        super(message);
    }
}
