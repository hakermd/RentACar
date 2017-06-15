package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum Options {
    BASE("BASE"),
    PREMIUM("PREMIUM"),
    FULL("FULL");

    private final String options;

    Options(String options) {
        this.options = options;
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
