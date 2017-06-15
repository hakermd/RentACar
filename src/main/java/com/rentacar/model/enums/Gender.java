package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum Gender {
    M("M"),
    W("W");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getValue() {
        return gender;
    }

    public boolean equalsName(String otherName) {
        return gender.equals(otherName);
    }

    public String toString() {
        return this.gender;
    }
}
