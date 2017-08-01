package com.rentacar.services;

import com.rentacar.dao.BookingRepository;
import com.rentacar.dao.CarRepository;
import com.rentacar.dao.RentRepository;
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
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addACar(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeACar(Car car) {
        carRepository.delete(car);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void suspendACar(Car car) {
        if (car.getAvailability().equalsName(CarAvailability.RENTED.toString())) {
            Rent rent = rentRepository.getRentByCarAndActiveTrue(car);
            Car updateCar = rent.getCar();
            updateCar.setAvailability(CarAvailability.BROKEN);
            rent.setActive(false);
            carRepository.save(updateCar);
            rentRepository.save(rent);
        } else if (car.getAvailability().equalsName(CarAvailability.BOOKED.toString())) {
            Booking booking = bookingRepository.getBookingByCarAndActiveTrue(car);
            Car updateCar = booking.getCar();
            updateCar.setAvailability(CarAvailability.BROKEN);
            booking.setActive(false);
            carRepository.save(updateCar);
            bookingRepository.save(booking);
        } else {
            car.setAvailability(CarAvailability.BROKEN);
            carRepository.save(car);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void unSuspendACar(Car car) {
        car.setAvailability(CarAvailability.AVAILABLE);
        carRepository.save(car);
    }

    @Override
    public Iterable<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> searchACar(CarFilter carFilter) {
        return carRepository.findACarByType(carFilter.getCarType());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeACarInfo(Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Car> searchACarByStatus(CarAvailability carAvailability) {
        return carRepository.findACarByAvailability(carAvailability);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelRent(Rent rent) {
        Car updateCar = updateCar(rent.getCar());
        rent.setActive(false);
        carRepository.save(updateCar);
        rentRepository.save(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelRentByPerson(Person person) {
        Rent rent = rentRepository.getRentByPersonAndActiveTrue(person);
        cancelRent(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelRentByCar(Car car) {
        Rent rent = rentRepository.getRentByCarAndActiveTrue(car);
        cancelRent(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBooking(Booking booking) {
        Car updateCar = updateCar(booking.getCar());
        booking.setActive(false);
        carRepository.save(updateCar);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByPerson(Person person) {
        Booking booking = bookingRepository.getBookingByPersonAndActiveTrue(person);
        cancelBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByCar(Car car) {
        Booking booking = bookingRepository.getBookingByCarAndActiveTrue(car);
        cancelBooking(booking);
    }

    @Override
    public Rent getRentByCar(Car car) {
        return rentRepository.getRentByCarAndActiveTrue(car);
    }

    @Override
    public Booking getBookingByCar(Car car) {
        return bookingRepository.getBookingByCarAndActiveTrue(car);
    }

    private Car updateCar(Car car) {
        car.setAvailability(CarAvailability.AVAILABLE);
        return car;
    }
}
