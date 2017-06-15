package com.rentacar.model.enums;

/**
 * Created by aplesca on 5/8/2017.
 */
public enum Options {
    BASE("BASE"), //base
    PREMIUM("PREMIUM"), //premium
    FULL("FULL"); //full

    private final String options;

    Options(String complectation) {
        this.options = complectation;
    }

    public String getValue() {
        return options;
    }

    public boolean equalsName(String otherName) {
        return options.equals(otherName);
    }

    public String toString() {
        return this.options;
    }
}
