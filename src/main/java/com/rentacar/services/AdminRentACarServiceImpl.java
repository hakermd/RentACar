package com.rentacar.services;

import com.rentacar.dao.BookingDao;
import com.rentacar.dao.CarDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AdminRentACarServiceImpl implements AdminRentACarService {
    @Autowired
    private CarDao carDao;
    @Autowired
    private RentDao rentDao;
    @Autowired
    private BookingDao bookingDao;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void addACar(Car car) {
        carDao.save(car);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void removeACar(Car car) {
        carDao.delete(car);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void suspendACar(Car car) {
        car.setAvailability(CarAvailability.BROKEN);
        carDao.update(car);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void changeACarInfo(Car car) {
        carDao.update(car);
    }

    @Override
    public List<Car> searchACarByStatus(CarAvailability carAvailability) {
        return carDao.searchACarByStatus(carAvailability);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelRent(Rent rent) {
        rent.getCar().setAvailability(CarAvailability.AVAILABLE);
        rent.setActive(false);
        rentDao.update(rent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelRentByPerson(Person person) {
        Rent rent = rentDao.getRentByPerson(person);
        cancelRent(rent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelRentByCar(Car car) {
        Rent rent = rentDao.getRentByCar(car);
        cancelRent(rent);
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
    public void cancelBookingByPerson(Person person) {
        Booking booking = bookingDao.getBookingByPerson(person);
        cancelBooking(booking);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByCar(Car car) {
        Booking booking = bookingDao.getBookingByCar(car);
        cancelBooking(booking);
    }
}
