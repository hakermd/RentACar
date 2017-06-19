package com.rentacar.services;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface CarService {
    public Car findCarByWinCode(String carWinCode);

    public List<Car> showAllAvailableCars();

    public List<Car> filterCars(CarFilter filter);

    public List<Car> showAllCars();

    public void saveCar(Car car);

    public void updateCar(Car car);

    public void deleteCar(Car car);
}
