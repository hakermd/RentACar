package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum FuelType {
    BENZINE("Benzine"),
    DIESEL("Diesel"),
    LPG("Autogas");

    private final String fuelType;

    FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getValue() {
        return fuelType;
    }

    public boolean equalsName(String otherName) {
        return fuelType.equals(otherName);
    }

    public String toString() {
        return this.fuelType;
    }
}
