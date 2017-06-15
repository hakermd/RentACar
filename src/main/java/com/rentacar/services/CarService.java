package com.rentacar.services;

import com.rentacar.model.Car;

import java.util.List;

public interface CarService {
    public List<Car> findAllCars();

    public void saveCar(Car car);

    public void updateCar(Car car);

    public void deleteCar(Car car);
}
