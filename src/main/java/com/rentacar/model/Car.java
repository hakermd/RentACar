package com.rentacar.model;

import com.rentacar.model.enums.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Andrei.Plesca
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
    @Column(name = "carPrice", nullable = false, precision = 2)
    private double carPrice = 0.0;
    @Column(name = "carAvailability", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarAvailability availability;

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
        this.winCode = winCode.toUpperCase();
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
        String carPhoto = manufacturer + "-" + model + ".png";
        return carPhoto.replaceAll(" ", "_").toLowerCase();
    }

    public double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(double carPrice) {
        this.carPrice = carPrice;
    }

    public CarAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(CarAvailability availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return engineVolume == car.engineVolume
                && Double.compare(car.carPrice, carPrice) == 0
                && (winCode != null ? winCode.equals(car.winCode) : car.winCode == null)
                && (manufacturer != null ? manufacturer.equals(car.manufacturer) : car.manufacturer == null)
                && (model != null ? model.equals(car.model) : car.model == null)
                && type == car.type
                && (yearOfProduction != null ? yearOfProduction.equals(car.yearOfProduction) : car.yearOfProduction == null)
                && (registrationNumber != null ? registrationNumber.equals(car.registrationNumber) : car.registrationNumber == null)
                && fuelType == car.fuelType
                && transmission == car.transmission
                && economyClass == car.economyClass
                && options == car.options
                && availability == car.availability;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = winCode != null ? winCode.hashCode() : 0;
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (yearOfProduction != null ? yearOfProduction.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        result = 31 * result + engineVolume;
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (transmission != null ? transmission.hashCode() : 0);
        result = 31 * result + (economyClass != null ? economyClass.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        temp = Double.doubleToLongBits(carPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        return result;
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
