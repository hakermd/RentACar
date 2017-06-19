package com.rentacar.dao;

import com.rentacar.model.Booking;

/**
 * Created by Andrei.Plesca
 */

public interface BookingDao extends DAO<Booking> {
    public Booking findBookingByCode(String bookingCode);
}
