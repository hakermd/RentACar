package com.rentacar.model;

import com.rentacar.model.enums.CarTransmission;
import com.rentacar.model.enums.CarType;
import com.rentacar.model.enums.EconomyClass;
import com.rentacar.model.enums.Options;

/**
 * Created by aplesca on 5/30/2017.
 */
public class CarFilter {
    private EconomyClass economyClass;
    //    private String manufacturer;
    private CarType carType;
    private Options options;
    private CarTransmission transmission;
    private int yearOfProduction;


    public EconomyClass getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(EconomyClass economyClass) {
        this.economyClass = economyClass;
    }

//    public String getManufacturer() {
//        return manufacturer;
//    }
//
//    public void setManufacturer(String manufacturer) {
//        this.manufacturer = manufacturer;
//    }

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

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}
