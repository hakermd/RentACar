package com.rentacar.model;

import com.rentacar.model.enums.*;

/**
 * Created by Andrei.Plesca
 */
public class CarFilter {
    private EconomyClass economyClass;
    private CarType carType;
    private Options options;
    private CarTransmission transmission;
    private CarAvailability carAvailability;
    private String yearOfProduction;

    public EconomyClass getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(EconomyClass economyClass) {
        this.economyClass = economyClass;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public CarTransmission getTransmission() {
        return transmission;
    }

    public void setTransmission(CarTransmission transmission) {
        this.transmission = transmission;
    }

    public CarAvailability getCarAvailability() {
        return carAvailability;
    }

    public void setCarAvailability(CarAvailability carAvailability) {
        this.carAvailability = carAvailability;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}
