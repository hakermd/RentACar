package com.rentacar.model;

import com.rentacar.model.enums.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by aplesca on 5/8/2017.
 */
@Entity
@Table(name = "car")
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "carId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carId;
    @Column(name = "winCode", unique = true, nullable = false)
    private String winCode;
    @Column(name = "carManufacturer", nullable = false)
    private String manufacturer;
    @Column(name = "carModel", nullable = false)
    private String model;
    @Column(name = "carType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarType type;
    @Column(name = "yearOfProduction", nullable = false)
    private String yearOfProduction;
    @Column(name = "registrationNumber", unique = true, nullable = false)
    private String registrationNumber;
    @Column(name = "engineVolume", nullable = false)
    private int engineVolume;
    @Column(name = "fuelType", nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Column(name = "transmission", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarTransmission transmission;
    @Column(name = "economyClass", nullable = false)
    @Enumerated(EnumType.STRING)
    private EconomyClass economyClass;
    @Column(name = "options", nullable = false)
    @Enumerated(EnumType.STRING)
    private Options options;
    @Column(name = "carPrice", nullable = false)
    private double carPrice = 0.0;
    @Column(name = "carAvailability", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarAvailability availability;
    @Transient
    private String carPhoto;

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getWinCode() {
        return winCode;
    }

    public void setWinCode(String winCode) {
        this.winCode = winCode;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(int engineVolume) {
        this.engineVolume = engineVolume;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public CarTransmission getTransmission() {
        return transmission;
    }

    public void setTransmission(CarTransmission transmission) {
        this.transmission = transmission;
    }

    public EconomyClass getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(EconomyClass economyClass) {
        this.economyClass = economyClass;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getCarPhoto() {
        carPhoto = manufacturer + "-" + model + ".png";
        return carPhoto.replaceAll(" ", "_").toLowerCase();
    }

    public double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice() {
        this.carPrice = economyClassPriceCalculate(this.economyClass) * optionsPriceCalculate(this.options);
    }

    private double economyClassPriceCalculate(EconomyClass economyClass) {
        switch (economyClass) {
            case ECONOMY:
                return 15;
            case PREMIUM:
                return 30;
            case BUSINESS:
                return 50;
        }
        return 0;
    }

    private double optionsPriceCalculate(Options complectation) {
        switch (complectation) {
            case BASE:
                return 1;
            case PREMIUM:
                return 1.5;
            case FULL:
                return 2;
        }
        return 0;
    }

    public CarAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(CarAvailability availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Car{" +
                "\n winCode='" + winCode + '\'' +
                ",\n manufacturer='" + manufacturer + '\'' +
                ", \n model='" + model + '\'' +
                ", \n type=" + type +
                ", \n yearOfProduction='" + yearOfProduction + '\'' +
                ", \n registrationNumber='" + registrationNumber + '\'' +
                ", \n engineVolume=" + engineVolume +
                ", \n transmission=" + transmission +
                ", \n economyClass=" + economyClass +
                ", \n options=" + options +
                ", \n carPrice=" + carPrice +
                ", \n availability=" + availability +
                "\n}";
    }
}
