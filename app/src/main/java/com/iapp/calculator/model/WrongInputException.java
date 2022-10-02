package com.iapp.calculator.model;

/**
 * Exception for invalid math expressions that cannot be calculated
 * @author Igor Ivanov
 * @version 1.0
 * */
public class WrongInputException extends Exception {

    public WrongInputException() {
        super();
    }

    public WrongInputException(String message) {
        super(message);
    }

    public WrongInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongInputException(Throwable cause) {
        super(cause);
    }
}
