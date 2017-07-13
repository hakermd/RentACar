package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
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
    private final String type;

    CarType(String carType) {
        this.type = carType;
    }

    public String getValue() {
        return type;
    }

    public boolean equalsName(String otherName) {
        return type.equals(otherName);
    }

    @Override
    public String toString() {
        return this.type;
    }
}

