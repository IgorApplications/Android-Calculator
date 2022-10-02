package com.iapp.calculator.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The class reads the given expression and returns the result.
 * @see Expression
 * @author Igor Ivanov
 * @version 1.0
 * */
public class Calculations {

    /** compiled expression from Expression */
    private List<String> tokens;

    public Calculations() {}

    /**
     * Performs calculations on compiled data from expression and returns the result.
     * @throws WrongInputException if the mathematical expression cannot be calculated
     * */
    public double calculate(Expression expression) throws WrongInputException {
        try {
            tokens = new ArrayList<>(expression.getTokens());
            while (hasBracket()) {
                var area = getBracketArea();
                calculateBracket(area);
            }

            var result = Double.parseDouble(calculateTokens(tokens).toString());
            if (tokens.size() > 1) throw new WrongInputException();
            return result;
        } catch (Throwable t) {
            var exc = new WrongInputException("invalid format for calculations");
            exc.addSuppressed(t);
            throw exc;
        }
    }

    private void calculateBracket(Pair<Integer, Integer> area) {
        var result = calculateTokens(tokens.subList(area.getFirst() + 1, area.getSecond()));
        System.out.println(tokens);
        System.out.println(area.getFirst() + " - " + (area.getFirst() + 2));
        removeArea(tokens, area.getFirst(), area.getFirst() + 2);
        tokens.add(area.getFirst(), String.valueOf(result));
    }

    private Pair<Integer, Integer> getBracketArea() {
        int start = -1, end = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("(")) {
                start = i;
            }
        }

        for (int i = start; i < tokens.size(); i++) {
            if (tokens.get(i).equals(")")) {
                end = i;
                break;
            }
        }

        return new Pair<>(start, end);
    }

    private boolean hasBracket() {
        return tokens.contains("(") && tokens.contains(")");
    }

    private BigDecimal calculateTokens(List<String> expression) {
        calculateFunctions(expression);
        return calculateArithmetic(expression);
    }

    private void calculateFunctions(List<String> expression) {
        for (int i = 0; i < expression.size(); i++) {
            switch (expression.get(i)) {
                case "^": {
                    var number = new BigDecimal(expression.get(i - 1));
                    number = number.pow(Integer.parseInt(expression.get(i + 1)));
                    expression.remove(i - 1);
                    expression.remove(i - 1);
                    expression.set(i - 1, String.valueOf(number));
                    i -= 2;
                    break;
                }
                case "√": {
                    var number = new BigDecimal(expression.get(i + 1));
                    number = BigDecimalUtil.sqrt(number, MathContext.DECIMAL128);
                    expression.remove(i);
                    expression.set(i, String.valueOf(number));
                    i--;
                    break;
                }
                case "sin": {
                    var number = Double.parseDouble(expression.get(i + 1));
                    number = Math.sin(Math.toRadians(number));
                    expression.remove(i);
                    expression.set(i, String.valueOf(number));
                    i--;
                    break;
                }
                case "cos": {
                    var number = Double.parseDouble(expression.get(i + 1));
                    number = Math.cos(Math.toRadians(number));
                    expression.remove(i);
                    expression.set(i, String.valueOf(number));
                    i--;
                    break;
                }
                case "tg": {
                    var number = Double.parseDouble(expression.get(i + 1));
                    number = Math.sin(Math.toRadians(number)) / Math.cos(Math.toRadians(number));
                    expression.remove(i);
                    expression.set(i, String.valueOf(number));
                    i--;
                    break;
                }
                case "ctg": {
                    var number = Double.parseDouble(expression.get(i + 1));
                    number = Math.cos(Math.toRadians(number)) / Math.sin(Math.toRadians(number));
                    expression.remove(i);
                    expression.set(i, String.valueOf(number));
                    i--;
                    break;
                }
            }
        }
    }

    private BigDecimal calculateArithmetic(List<String> expression) {
        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("*")) {
                var first = new BigDecimal(expression.get(i - 1));
                var second = new BigDecimal(expression.get(i + 1));
                expression.remove(i - 1);
                expression.remove(i - 1);
                expression.set(i - 1, String.valueOf(first.multiply(second, MathContext.DECIMAL128)));
                i -= 2;
            } else if (expression.get(i).equals(":")) {
                var first = new BigDecimal(expression.get(i - 1));
                var second = new BigDecimal(expression.get(i + 1));
                expression.remove(i - 1);
                expression.remove(i - 1);
                expression.set(i - 1, String.valueOf(first.divide(second, MathContext.DECIMAL128)));
                i -= 2;
            }
        }

        for (int i = 0; i < expression.size(); i++) {
            if (expression.get(i).equals("+")) {
                var first = new BigDecimal(expression.get(i - 1));
                var second = new BigDecimal(expression.get(i + 1));
                expression.remove(i - 1);
                expression.remove(i - 1);
                expression.set(i - 1, String.valueOf(first.add(second, MathContext.DECIMAL128)));
                i -= 2;
            } else if (expression.get(i).equals("-")) {
                var first = new BigDecimal(expression.get(i - 1));
                var second = new BigDecimal(expression.get(i + 1));
                expression.remove(i - 1);
                expression.remove(i - 1);
                expression.set(i - 1, String.valueOf(first.subtract(second, MathContext.DECIMAL128)));
                i -= 2;
            }
        }

        return new BigDecimal(expression.get(0));
    }

    private void removeArea(List<?> list, int start, int end) {
        for (int i = start; i <= end; i++) list.remove(start);
    }
}


