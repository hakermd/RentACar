package com.rentacar.dao;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;

/**
 * Created by Andrei.Plesca
 */

public interface BookingDao extends DAO<Booking> {
    Booking findBookingByCode(String bookingCode);

    Booking getBookingByCar(Car car);

    Booking getBookingByPerson(Person person);
}
