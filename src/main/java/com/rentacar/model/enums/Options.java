package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum Options {
    BASE("BASE"),
    PREMIUM("PREMIUM"),
    FULL("FULL");

    private final String opt;

    Options(String options) {
        this.opt = options;
    }

    public String getValue() {
        return opt;
    }

    public boolean equalsName(String otherName) {
        return opt.equals(otherName);
    }

    @Override
    public String toString() {
        return this.opt;
    }
}
