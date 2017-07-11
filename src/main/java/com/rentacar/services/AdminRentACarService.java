package com.rentacar.services;

import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface AdminRentACarService {
    void addACar(Car car);

    void removeACar(Car car);

    void suspendACar(Car car);

    void unsuspendACar(Car car);

    List<Car> getAllCars();

    List<Car> searchACar(CarFilter carFilter);

    void changeACarInfo(Car car);

    List<Car> searchACarByStatus(CarAvailability carAvailability);

    void cancelRent(Rent rent);

    void cancelRentByPerson(Person person);

    void cancelRentByCar(Car car);

    void cancelBooking(Booking booking);

    void cancelBookingByPerson(Person person);

    void cancelBookingByCar(Car car);

    Rent getRentByCar(Car car);

    Booking getBookingByCar(Car car);

}
