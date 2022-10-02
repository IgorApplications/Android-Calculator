package com.iapp.calculator.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * The class is designed to perform additional actions with BigDecimal
 * @author Luciano Culacciatti
 * @version 1.0
 * */
class BigDecimalUtil {

    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    /**
     * Uses Newton Raphson to compute the square root of a BigDecimal.
     * @author Luciano Culacciatti
     */
    static BigDecimal sqrt(BigDecimal c, MathContext mc) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE, mc));
    }

    /**
     * Private utility method used to compute the square root of a BigDecimal.
     * @author Luciano Culacciatti
     */
    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision){
        var fx = xn.pow(2).add(c.negate());
        var fpx = xn.multiply(new BigDecimal(2));
        var xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        var currentSquare = xn1.pow(2);
        var currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1){
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }
}
