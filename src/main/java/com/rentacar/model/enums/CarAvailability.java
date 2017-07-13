package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum CarAvailability {
    AVAILABLE("AVAILABLE"),
    RENTED("RENTED"),
    BROKEN("BROKEN"),
    BOOKED("BOOKED");
    private final String availability;

    CarAvailability(String carAvailability) {
        this.availability = carAvailability;
    }

    public String getValue() {
        return availability;
    }

    public boolean equalsName(String otherName) {
        return availability.equals(otherName);
    }

    @Override
    public String toString() {
        return this.availability;
    }
}
