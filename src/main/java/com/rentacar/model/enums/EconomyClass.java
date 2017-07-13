package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum EconomyClass {
    ECONOMY("ECONOMY"),
    PREMIUM("PREMIUM"),
    BUSINESS("BUSINESS");

    private final String economy;

    EconomyClass(String economyClass) {
        this.economy = economyClass;
    }

    public String getValue() {
        return economy;
    }

    public boolean equalsName(String otherName) {
        return economy.equals(otherName);
    }

    @Override
    public String toString() {
        return this.economy;
    }
}
