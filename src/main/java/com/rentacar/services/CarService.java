package com.rentacar.services;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface CarService {
    Car findCarById(long carId);

    Car findCarByWinCode(String carWinCode);

    Car findCarByRegistrationNumber(String registrationNumber);

    List<Car> showAllAvailableCars();

    List<Car> filterCars(CarFilter filter);

    List<Car> showAllCars();

    void saveCar(Car car);

    void updateCar(Car car);

    void deleteCar(Car car);
}
