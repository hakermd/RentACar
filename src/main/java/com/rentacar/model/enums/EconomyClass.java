package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum EconomyClass {
    ECONOMY("ECONOMY"),
    PREMIUM("PREMIUM"),
    BUSINESS("BUSINESS");

    private final String economyClass;

    EconomyClass(String economyClass) {
        this.economyClass = economyClass;
    }

    public String getValue() {
        return economyClass;
    }

    public boolean equalsName(String otherName) {
        return economyClass.equals(otherName);
    }

    public String toString() {
        return this.economyClass;
    }
}
