package com.rentacar.services;

import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface AdminRentACarService {
    public void addACar(Car car);

    public void removeACar(Car car);

    public void suspendACar(Car car);

    public void unsuspendACar(Car car);

    public List<Car> getAllCars();

    public List<Car> searchACar(CarFilter carFilter);

    public void changeACarInfo(Car car);

    public List<Car> searchACarByStatus(CarAvailability carAvailability);

    public void cancelRent(Rent rent);

    public void cancelRentByPerson(Person person);

    public void cancelRentByCar(Car car);

    public void cancelBooking(Booking booking);

    public void cancelBookingByPerson(Person person);

    public void cancelBookingByCar(Car car);

    public Rent getRentByCar(Car car);

    public Booking getBookingByCar(Car car);

}
