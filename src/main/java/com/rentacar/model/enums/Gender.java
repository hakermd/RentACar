package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum Gender {
    M("M"),
    W("W");

    private final String gen;

    Gender(String gender) {
        this.gen = gender;
    }

    public String getValue() {
        return gen;
    }

    public boolean equalsName(String otherName) {
        return gen.equals(otherName);
    }

    @Override
    public String toString() {
        return this.gen;
    }
}
