package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum CarTransmission {
    AUTOMATIC("AUTOMATIC"),
    MANUAL("MANUAL");
    private final String carTransmission;

    CarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }

    public String getValue() {
        return carTransmission;
    }

    public boolean equalsName(String otherName) {
        return carTransmission.equals(otherName);
    }

    public String toString() {
        return this.carTransmission;
    }
}
