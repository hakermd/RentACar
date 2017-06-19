package com.rentacar.services;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Rent;

/**
 * Created by Andrei.Plesca
 */
public interface UserRentACarService {


    public void rentACar(Rent rent);

    public void rentACarByBookingCode(String bookingCode);

    public String bookACar(Booking book); //return bookingId

    public void returnACar(Car car);

    public void cancelBooking(Booking booking);

    public void cancelBookingByCode(String bookingCode);

}
