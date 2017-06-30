package com.rentacar.services;

import com.rentacar.dao.BookingDao;
import com.rentacar.dao.CarDao;
import com.rentacar.dao.InsuranceDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.*;
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
    private InsuranceDao insuranceDao;
    @Autowired
    private CarDao carDao;
    @Autowired
    private BookingDao bookingDao;

    @Override
    public Rent getRentByPerson(Person person) {
        return rentDao.getRentByPerson(person);
    }

    @Override
    public Booking getBookingByPerson(Person person) {
        return bookingDao.getBookingByPerson(person);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void rentACar(Car car, Person person) {
        car.setAvailability(CarAvailability.RENTED);
            /*------------------------------*/
        Insurance insurance = new Insurance();
        insurance.setCar(car);
        insurance.setPerson(person);
        insurance.setCost(insuranceCostCalculate(insurance));
        insuranceDao.save(insurance);
            /*------------------------------*/
        Rent rent = new Rent();
        rent.setActive(true);
        rent.setCar(car);
        rent.setInsurance(insurance);
        rent.setCost(car.getCarPrice() + insurance.getCost());
        rent.setPerson(person);
        carDao.update(car);
        rentDao.save(rent);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void rentACarByBookingCode(String bookingCode) {
        Booking booking = bookingDao.findBookingByCode(bookingCode);
        Car updateCar = booking.getCar();
        updateCar.setAvailability(CarAvailability.RENTED);
        booking.setActive(false);
        Rent rent = new Rent();
        rent.setCar(booking.getCar());
        rent.setPerson(booking.getPerson());
        rent.setInsurance(booking.getInsurance());
        rent.setCost(booking.getCost());
        rent.setActive(true);
        bookingDao.delete(booking);
        carDao.update(updateCar);
        rentDao.save(rent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String bookACar(Car car, Person person) {
        car.setAvailability(CarAvailability.BOOKED);
            /*------------------------------*/
        Insurance insurance = new Insurance();
        insurance.setCar(car);
        insurance.setPerson(person);
        insurance.setCost(insuranceCostCalculate(insurance));
        insuranceDao.save(insurance);
            /*------------------------------*/
        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCar(car);
        booking.setInsurance(insurance);
        booking.setCost(car.getCarPrice() + insurance.getCost());
        booking.setPerson(person);
        carDao.update(car);
        bookingDao.save(booking);
        return booking.getBookingCode();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void returnACar(Car car) {
        Rent rent = rentDao.getRentByCar(car);
        Car updateCar = rent.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        rent.setActive(false);
        carDao.update(updateCar);
        rentDao.update(rent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void deleteRent(Rent rent) {
        Car updateCar = rent.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        insuranceDao.delete(rent.getInsurance());
        rentDao.delete(rent);
        carDao.update(updateCar);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelBooking(Booking booking) {
        Car updateCar = booking.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        booking.setActive(false);
        carDao.update(updateCar);
        bookingDao.update(booking);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByCode(String bookingCode) {
        Booking booking = bookingDao.findBookingByCode(bookingCode);
        cancelBooking(booking);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void deleteBooking(Booking booking) {
        Car updateCar = booking.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        insuranceDao.delete(booking.getInsurance());
        bookingDao.delete(booking);
        carDao.update(booking.getCar());
    }

    public double insuranceCostCalculate(Insurance insurance) {
        Car car = insurance.getCar();
        switch (car.getEconomyClass()) {
            case ECONOMY:
                return 0.3 * car.getCarPrice();
            case PREMIUM:
                return 0.5 * car.getCarPrice();
            case BUSINESS:
                return 0.7 * car.getCarPrice();
        }
        return 0;
    }
}
