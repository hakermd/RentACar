package com.rentacar.services;

import com.rentacar.dao.BookingDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Rent;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrei.Plesca
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserRentACarServiceImpl implements UserRentACarService {
    @Autowired
    private RentDao rentDao;
    @Autowired
    private BookingDao bookingDao;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void rentACar(Rent rent) {
        rentDao.save(rent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String bookACar(Booking booking) {
        bookingDao.save(booking);
        return booking.getBookingCode();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void returnACar(Car car) {
        Rent rent = rentDao.getRentByCar(car);
        rent.getCar().setAvailability(CarAvailability.AVAILABLE);
        rent.setActive(false);
        rentDao.update(rent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelBooking(Booking booking) {
        booking.getCar().setAvailability(CarAvailability.AVAILABLE);
        booking.setActive(false);
        bookingDao.update(booking);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByCode(String bookingCode) {
        Booking booking = bookingDao.findBookingByCode(bookingCode);
        cancelBooking(booking);
    }
}
