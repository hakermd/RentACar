package com.rentacar.services;

import com.rentacar.dao.BookingDao;
import com.rentacar.dao.CarDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
public class AdminRentACarServiceImpl implements AdminRentACarService {
    @Autowired
    private CarDao carDao;
    @Autowired
    private RentDao rentDao;
    @Autowired
    private BookingDao bookingDao;


    @Override
    public void addACar(Car car) {
        carDao.save(car);
    }

    @Override
    public void removeACar(Car car) {
        carDao.delete(car);
    }

    @Override
    public void suspendACar(Car car) {
        car.setAvailability(CarAvailability.BROKEN);
        carDao.update(car);
    }

    @Override
    public void unsuspendACar(Car car) {
        car.setAvailability(CarAvailability.AVAILABLE);
        carDao.update(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.findAll();
    }

    @Override
    public List<Car> searchACar(CarFilter carFilter) {
        return carDao.searchACarByCriteria(carFilter);
    }

    @Override
    public void changeACarInfo(Car car) {
        carDao.update(car);
    }

    @Override
    public List<Car> searchACarByStatus(CarAvailability carAvailability) {
        return carDao.searchACarByStatus(carAvailability);
    }

    @Override
    public void cancelRent(Rent rent) {
        rent.getCar().setAvailability(CarAvailability.AVAILABLE);
        rent.setActive(false);
        rentDao.update(rent);
    }

    @Override
    public void cancelRentByPerson(Person person) {
        Rent rent = rentDao.getRentByPerson(person);
        cancelRent(rent);
    }

    @Override
    public void cancelRentByCar(Car car) {
        Rent rent = rentDao.getRentByCar(car);
        cancelRent(rent);
    }

    @Override
    public void cancelBooking(Booking booking) {
        booking.getCar().setAvailability(CarAvailability.AVAILABLE);
        booking.setActive(false);
        bookingDao.update(booking);
    }

    @Override
    public void cancelBookingByPerson(Person person) {
        Booking booking = bookingDao.getBookingByPerson(person);
        cancelBooking(booking);
    }

    @Override
    public void cancelBookingByCar(Car car) {
        Booking booking = bookingDao.getBookingByCar(car);
        cancelBooking(booking);
    }
}
