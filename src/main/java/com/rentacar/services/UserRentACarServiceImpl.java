package com.rentacar.services;

import com.rentacar.dao.BookingRepository;
import com.rentacar.dao.CarRepository;
import com.rentacar.dao.InsuranceRepository;
import com.rentacar.dao.RentRepository;
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
    private RentRepository rentRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Rent getRentByPerson(Person person) {
        return rentRepository.getRentByPersonAndActiveTrue(person);
    }

    @Override
    public Booking getBookingByPerson(Person person) {
        return bookingRepository.getBookingByPersonAndActiveTrue(person);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rentACar(Car car, Person person) {
        car.setAvailability(CarAvailability.RENTED);

        Insurance insurance = new Insurance();
        insurance.setCar(car);
        insurance.setPerson(person);
        insurance.setCost(insuranceCostCalculate(insurance));
        insuranceRepository.save(insurance);

        Rent rent = new Rent();
        rent.setActive(true);
        rent.setCar(car);
        rent.setInsurance(insurance);
        rent.setCost(car.getCarPrice() + insurance.getCost());
        rent.setPerson(person);
        carRepository.save(car);
        rentRepository.save(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void rentACarByBookingCode(String bookingCode) {
        Booking booking = bookingRepository.findBookingByBookingCode(bookingCode);
        Car updateCar = booking.getCar();
        updateCar.setAvailability(CarAvailability.RENTED);

        booking.setActive(false);
        Rent rent = new Rent();
        rent.setCar(booking.getCar());
        rent.setPerson(booking.getPerson());
        rent.setInsurance(booking.getInsurance());
        rent.setCost(booking.getCost());
        rent.setActive(true);

        bookingRepository.delete(booking);
        carRepository.save(updateCar);
        rentRepository.save(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String bookACar(Car car, Person person) {
        car.setAvailability(CarAvailability.BOOKED);

        Insurance insurance = new Insurance();
        insurance.setCar(car);
        insurance.setPerson(person);
        insurance.setCost(insuranceCostCalculate(insurance));
        insuranceRepository.save(insurance);

        Booking booking = new Booking();
        booking.setActive(true);
        booking.setCar(car);
        booking.setInsurance(insurance);
        booking.setCost(car.getCarPrice() + insurance.getCost());
        booking.setPerson(person);
        carRepository.save(car);
        bookingRepository.save(booking);
        return booking.getBookingCode();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void returnACar(Car car) {
        Rent rent = rentRepository.getRentByCarAndActiveTrue(car);
        Car updateCar = rent.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        rent.setActive(false);
        carRepository.save(updateCar);
        rentRepository.save(rent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteRent(Rent rent) {
        Car updateCar = rent.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        insuranceRepository.delete(rent.getInsurance());
        rentRepository.delete(rent);
        carRepository.save(updateCar);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBooking(Booking booking) {
        Car updateCar = booking.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        booking.setActive(false);
        carRepository.save(updateCar);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBookingByCode(String bookingCode) {
        Booking booking = bookingRepository.findBookingByBookingCode(bookingCode);
        cancelBooking(booking);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteBooking(Booking booking) {
        Car updateCar = booking.getCar();
        updateCar.setAvailability(CarAvailability.AVAILABLE);
        insuranceRepository.delete(booking.getInsurance());
        bookingRepository.delete(booking);
        carRepository.save(booking.getCar());
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
            default:
                return 0;
        }
    }
}
