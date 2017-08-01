package com.rentacar.dao;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Andrei.Plesca
 */

public interface BookingRepository extends CrudRepository<Booking, Long> {
    Booking findBookingByBookingCode(String bookingCode);

    Booking getBookingByCarAndActiveTrue(Car car);

    Booking getBookingByPersonAndActiveTrue(Person person);
}
