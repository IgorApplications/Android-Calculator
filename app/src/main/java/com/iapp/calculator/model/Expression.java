package com.iapp.calculator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The immutable class of the processed mathematical expression.
 * Supports such mathematical operations as:
 * sin, cos, tg, ctg, √, ^, +, -, *, :, (, )
 * @author Igor Ivanov
 * @version 1.0
 * */
public class Expression {

    /** compiled mathematical expression into separate elements */
    private final List<String> tokens;

    /** Compiles a mathematical expression into separate
     *  elements and returns an immutable instance.
     *  @throws WrongInputException if the mathematical expression cannot be compiled
     * */
    public static Expression compile(String expression) throws WrongInputException {
        List<String> tokens;
        try {
            tokens = convertToTokens(expression);
        } catch (Throwable t) {
            var exc = new WrongInputException("invalid format for processing");
            exc.addSuppressed(t);
            throw exc;
        }
        return new Expression(tokens);
    }

    List<String> getTokens() {
        return tokens;
    }

    private static List<String> convertToTokens(String expression) {
        expression = expression.replaceAll("\\s", "").replaceAll(",", "\\.")
                .replaceAll("sin", "s")
                .replaceAll("cos", "c")
                .replaceAll("ctg", "g")
                .replaceAll("tg", "t");

        var signs = expression.replaceAll("[^scgt\\(\\)^√\\*:\\+-]", "").split("");
        var numbers = expression.replaceAll("[sctg\\(\\)^√\\*:\\+-]", "z#z").split("z");

        var signsList = new ArrayList<>(Arrays.asList(signs));
        var numbersList = new ArrayList<>(Arrays.asList(numbers));

        signsList.removeIf(el -> el.equals(""));
        numbersList.removeIf(el -> el.equals(""));

        for (int i = 0; i < signsList.size(); i++) {
            if (signsList.get(i).equals("s")) signsList.set(i, "sin");
            else if (signsList.get(i).equals("c")) signsList.set(i, "cos");
            else if (signsList.get(i).equals("g")) signsList.set(i, "ctg");
            else if (signsList.get(i).equals("t")) signsList.set(i, "tg");
        }

        return connectExpression(numbersList, signsList);
    }

    private static List<String> connectExpression(List<String> numbers, List<String> signs) {
        int j = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals("#")) {
                numbers.set(i, signs.get(j++));
            }
        }

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals("-")) {
                if (i == 0) {
                    numbers.add(0, "(");
                    numbers.add( 1, "0");
                    numbers.add(4, ")");
                    i+=3;
                } else if (numbers.get(i - 1).matches("([\\(\\)^√\\*:\\+-])|(sin)|(cos)|(tg)|(ctg)")) {
                    numbers.add(i, "(");
                    numbers.add(i + 1, "0");
                    numbers.add(i + 4, ")");
                    i+=3;
                }
            }
        }

        return numbers;
    }

    private Expression(List<String> tokens) {
        this.tokens = Collections.unmodifiableList(tokens);
    }
}


