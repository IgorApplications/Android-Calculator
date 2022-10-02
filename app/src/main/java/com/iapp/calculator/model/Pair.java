package com.iapp.calculator.model;

/**
 * Immutable storage for two elements with specified data type
 * @author Igor Ivanov
 * @version 1.0
 * */
public class Pair<F, S> {

    /** first element */
    private final F first;
    /** second element */
    private final S second;

    /** initializing an immutable object */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /** returns the first element */
    public F getFirst() {
        return first;
    }

    /** returns the second element */
    public S getSecond() {
        return second;
    }
}
