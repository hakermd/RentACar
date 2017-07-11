package com.rentacar.services;

import com.rentacar.model.*;

/**
 * Created by Andrei.Plesca
 */
public interface UserRentACarService {

    Rent getRentByPerson(Person person);

    Booking getBookingByPerson(Person person);

    void rentACar(Car car, Person person);

    void rentACarByBookingCode(String bookingCode);

    String bookACar(Car car, Person person);

    void returnACar(Car car);

    void deleteRent(Rent rent);

    void cancelBooking(Booking booking);

    void cancelBookingByCode(String bookingCode);

    void deleteBooking(Booking booking);

    double insuranceCostCalculate(Insurance insurance);

}
