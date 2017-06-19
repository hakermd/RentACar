package com.rentacar.dao;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;

/**
 * Created by Andrei.Plesca
 */

public interface BookingDao extends DAO<Booking> {
    public Booking findBookingByCode(String bookingCode);

    public Booking getBookingByCar(Car car);

    public Booking getBookingByPerson(Person person);
}
