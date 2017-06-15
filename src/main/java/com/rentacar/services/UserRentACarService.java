package com.rentacar.services;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.Rent;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface UserRentACarService {
    public List<Car> showAllCars();

    public List<Car> showAllAvailableCars();

    public List<Car> searchACar(CarFilter filter);

    public void rentACar(Rent rent);

    public Car rentACarByBookingId(int bookingId);

    public int bookACar(Car car); //return bookingId

    public void returnACar(Car car);

    public void cancelBooking(int bookingId);

}
