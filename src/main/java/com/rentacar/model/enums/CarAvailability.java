package com.rentacar.model.enums;

/**
 * Created by aplesca on 5/8/2017.
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
        // (otherName == null) check is not needed because name.equals(null) returns false
        return carAvailability.equals(otherName);
    }

    public String toString() {
        return this.carAvailability;
    }
}
