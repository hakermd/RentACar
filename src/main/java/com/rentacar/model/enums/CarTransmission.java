package com.rentacar.model.enums;

/**
 * Created by aplesca on 5/22/2017.
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
