package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum CarAvailability {
    AVAILABLE("AVAILABLE"),
    RENTED("RENTED"),
    BROKEN("BROKEN"),
    BOOKED("BOOKED");
    private final String carAvailability;

    CarAvailability(String carAvailability) {
        this.carAvailability = carAvailability;
    }

    public String getValue() {
        return carAvailability;
    }

    public boolean equalsName(String otherName) {
        return carAvailability.equals(otherName);
    }

    public String toString() {
        return this.carAvailability;
    }
}
