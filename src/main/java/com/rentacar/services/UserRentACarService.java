package com.rentacar.services;

import com.rentacar.model.*;

/**
 * Created by Andrei.Plesca
 */
public interface UserRentACarService {

    public Rent getRentByPerson(Person person);

    public Booking getBookingByPerson(Person person);

    public void rentACar(Car car, Person person);

    public void rentACarByBookingCode(String bookingCode);

    public String bookACar(Car car, Person person); //return bookingId

    public void returnACar(Car car);

    public void deleteRent(Rent rent);

    public void cancelBooking(Booking booking);

    public void cancelBookingByCode(String bookingCode);

    public void deleteBooking(Booking booking);

    double insuranceCostCalculate(Insurance insurance);

}
