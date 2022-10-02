package com.iapp.calculator.controller;

import com.iapp.calculator.model.Calculations;
import com.iapp.calculator.model.Expression;
import com.iapp.calculator.model.WrongInputException;

/**
 * Calculator business logic control
 * @author Igor Ivanov
 * @version 1.0
 * */
public class Controller {

    /** Class for performing mathematical calculations */
    private final Calculations calculations;

    public Controller() {
        calculations = new Calculations();
    }

    /**
     * compiles the expression and performs the calculation, then returns the result.
     * @throws WrongInputException if the expression has invalid syntax
     * */
    public String calculate(String expText) throws WrongInputException {
        var expression = Expression.compile(expText);
        var result = String.valueOf(calculations.calculate(expression));
        return result.replaceAll("(\\.+0+)$", "").replaceAll("\\.", ",");
    }
}
