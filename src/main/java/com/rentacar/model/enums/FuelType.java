package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum FuelType {
    BENZINE("BENZINE"),
    DIESEL("DIESEL"),
    GAS("GAS");

    private final String fuel;

    FuelType(String fuelType) {
        this.fuel = fuelType;
    }

    public String getValue() {
        return fuel;
    }

    public boolean equalsName(String otherName) {
        return fuel.equals(otherName);
    }

    @Override
    public String toString() {
        return this.fuel;
    }
}
