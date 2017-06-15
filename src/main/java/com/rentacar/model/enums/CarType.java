package com.rentacar.model.enums;

/**
 * Created by aplesca on 5/8/2017.
 */
public enum CarType {
    SEDAN("SEDAN"),
    COUPE("COUPE"),
    CABRIOLET("CABRIOLET"),
    UNIVERSAL("UNIVERSAL"),
    HATCHBACK("HATCHBACK"),
    CROSSOVER("CROSSOVER"),
    MINIVAN("MINIVAN"),
    SUV("SUV");
    private final String carType;

    CarType(String carType) {
        this.carType = carType;
    }

    public String getValue() {
        return carType;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return carType.equals(otherName);
    }

    public String toString() {
        return this.carType;
    }
}
