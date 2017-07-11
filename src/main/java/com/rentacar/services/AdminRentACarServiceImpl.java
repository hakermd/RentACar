package com.rentacar.services;

import com.rentacar.dao.BookingDao;
import com.rentacar.dao.CarDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.*;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AdminRentACarServiceImpl implements AdminRentACarService {
    private final CarDao carDao;
    private final RentDao rentDao;
    private final BookingDao bookingDao;

    @Autowired
    public AdminRentACarServiceImpl(CarDao carDao, RentDao rentDao, BookingDao bookingDao) {
        this.carDao = carDao;
        this.rentDao = rentDao;
        this.bookingDao = bookingDao;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addACar(Car car) {
        carDao.save(car);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeACar(Car car) {
        carDao.delete(car);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void suspendACar(Car car) {
        if (car.getAvailability().equalsName(CarAvailability.RENTED.toString())) {
            Rent rent = rentDao.getRentByCar(car);
            Car updateCar = rent.getCar();
            updateCar.setAvailability(CarAvailability.BROKEN);
            rent.setActive(false);
            carDao.update(updateCar);
            rentDao.update(rent);
        } else if (car.getAvailability().equalsName(CarAvailability.BOOKED.toString())) {
            Booking booking = bookingDao.getBookingByCar(car);
            Car updateCar = booking.getCar();
            updateCar.setAvailability(CarAvailability.BROKEN);
            booking.setActive(false);
            carDao.update(updateCar);
            bookingDao.update(booking);
        } else {
            car.setAvailability(CarAvailability.BROKEN);
            carDao.update(car);
        }
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
        return (List<Car>) carDao.searchACarByCriteria(carFilter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeACarInfo(Car car) {
        carDao.update(car);
    }

    @Override
    public List<Car> searchACarByStatus(CarAvailability carAvailability) {
        return carDao.searchACarByStatus(carAvailability);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelRent(Rent rent) {
        Car updateCar = updateCar(rent.getCar());
        rent.setActive(false);
        carDao.update(updateCar);
        rentDao.update(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelRentByPerson(Person person) {
        Rent rent = rentDao.getRentByPerson(person);
        cancelRent(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelRentByCar(Car car) {
        Rent rent = rentDao.getRentByCar(car);
        cancelRent(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBooking(Booking booking) {
        Car updateCar = updateCar(booking.getCar());
        booking.setActive(false);
        carDao.update(updateCar);
        bookingDao.update(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByPerson(Person person) {
        Booking booking = bookingDao.getBookingByPerson(person);
        cancelBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByCar(Car car) {
        Booking booking = bookingDao.getBookingByCar(car);
        cancelBooking(booking);
    }

    @Override
    public Rent getRentByCar(Car car) {
        return rentDao.getRentByCar(car);
    }

    @Override
    public Booking getBookingByCar(Car car) {
        return bookingDao.getBookingByCar(car);
    }

    private Car updateCar(Car car) {
        car.setAvailability(CarAvailability.AVAILABLE);
        return car;
    }
}
