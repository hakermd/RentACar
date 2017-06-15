package com.rentacar.services;

import com.rentacar.model.Car;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface AdminRentACarService {
    public List<Car> addACar(Car car);
    public void removeACar(Car car);
    public List<Car> suspendACar(Car car);
    public List<Car> unsuspendACar(Car car);
    public List<Car> getAllCars();
    public List<Car> searchACar(Car car);
    public List<Car> changeACarInfo(Car car);
    public List<Car> searchACarByStatus(Car car);
}
