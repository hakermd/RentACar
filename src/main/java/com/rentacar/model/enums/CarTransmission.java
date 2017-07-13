package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum CarTransmission {
    AUTOMATIC("AUTOMATIC"),
    MANUAL("MANUAL");
    private final String transmission;

    CarTransmission(String carTransmission) {
        this.transmission = carTransmission;
    }

    public String getValue() {
        return transmission;
    }

    public boolean equalsName(String otherName) {
        return transmission.equals(otherName);
    }

    @Override
    public String toString() {
        return this.transmission;
    }
}
