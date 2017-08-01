package com.rentacar.services;

import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;

/**
 * Created by Andrei.Plesca
 */
public interface AdminRentACarService {
    void addACar(Car car);

    void removeACar(Car car);

    void suspendACar(Car car);

    void unSuspendACar(Car car);

    Iterable<Car> getAllCars();

    Iterable<Car> searchACar(CarFilter carFilter);

    void changeACarInfo(Car car);

    Iterable<Car> searchACarByStatus(CarAvailability carAvailability);

    void cancelRent(Rent rent);

    void cancelRentByPerson(Person person);

    void cancelRentByCar(Car car);

    void cancelBooking(Booking booking);

    void cancelBookingByPerson(Person person);

    void cancelBookingByCar(Car car);

    Rent getRentByCar(Car car);

    Booking getBookingByCar(Car car);

}
