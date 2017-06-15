package com.rentacar.services;

import com.rentacar.model.Car;

import java.util.List;

/**
 * Created by aplesca on 5/8/2017.
 */
public interface AdminRentACarService {
    public List<Car> addACar(Car car); //return bookingId
    public void removeACar(Car car);
    public List<Car> suspendACar(Car car); //return bookingId
    public List<Car> unsuspendACar(Car car); //return bookingId
    public List<Car> getAllCars();
    public List<Car> searchACar(Car car);
    public List<Car> changeACarInfo(Car car); //return bookingId
    public List<Car> searchACarByStatus(Car car);
}
