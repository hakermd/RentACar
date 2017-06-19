package com.rentacar.services;

import com.rentacar.dao.BookingDao;
import com.rentacar.dao.CarDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Rent;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Andrei.Plesca
 */
@Service
public class UserRentACarServiceImpl implements UserRentACarService {
    @Autowired
    private CarDao carDao;
    @Autowired
    private RentDao rentDao;
    @Autowired
    private BookingDao bookingDao;

    @Override
    public void rentACar(Rent rent) {
        rentDao.save(rent);
    }

    @Override
    public void rentACarByBookingCode(String bookingCode) {
        Booking booking = bookingDao.findBookingByCode(bookingCode);
        booking.getCar().setAvailability(CarAvailability.RENTED);
        booking.setActive(false);
        Rent rent = new Rent();
        rent.setCar(booking.getCar());
        rent.setPerson(booking.getPerson());
        rent.setInsurance(booking.getInsurance());
        rent.setCost(booking.getCost());
        rent.setActive(true);
        bookingDao.update(booking);
        rentACar(rent);
    }

    @Override
    public String bookACar(Booking booking) {
        bookingDao.save(booking);
        return booking.getBookingCode();
    }

    @Override
    public void returnACar(Car car) {
        car.setAvailability(CarAvailability.AVAILABLE);
        carDao.update(car);
    }

    @Override
    public void cancelBooking(Booking booking) {
        booking.getCar().setAvailability(CarAvailability.AVAILABLE);
        booking.setActive(false);
        bookingDao.update(booking);
    }

    @Override
    public void cancelBookingByCode(String bookingCode) {
        Booking booking = bookingDao.findBookingByCode(bookingCode);
        cancelBooking(booking);
    }
}
