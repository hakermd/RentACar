package com.rentacar.services;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.testutils.TestDataUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * Created by Andrei.Plesca
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
public class UserRentACarServiceTest {
    @Autowired
    private UserRentACarService userRentACarService;
    @Autowired
    private CarService carService;
    @Autowired
    private PersonService personService;

    private Rent rent;
    private Rent currentRent;

    private Booking booking;
    private Booking currentBooking;

    private Car car;
    private Car currentCar;
    private Person currentPerson;
    private Person person;

    @Before
    public void setUp() throws ParseException {
        car = TestDataUtil.getMockCar();
        person = TestDataUtil.getMockPerson();

        personService.savePerson(person);
        carService.saveCar(car);
        currentPerson = personService.findById(person.getPersonId());
        currentCar = carService.findCarById(car.getCarId());
    }

    @After
    public void cleanUp() {
        if (rent != null) {
            userRentACarService.deleteRent(rent);
            booking = null;
        }
        if (booking != null) {
            userRentACarService.deleteBooking(booking);
        }

        personService.deletePerson(currentPerson);
        carService.deleteCar(currentCar);
    }

    @Test
    public void rentACar() throws Exception {
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.rentACar(currentCar, currentPerson);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
        rent = userRentACarService.getRentByPerson(currentPerson);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);
    }

    @Test
    public void rentACarByBookingCode() throws Exception {
        String bookingCode = userRentACarService.bookACar(currentCar, currentPerson);
        assertNotNull(bookingCode);
        booking = userRentACarService.getBookingByPerson(currentPerson);
        assertNotNull(booking);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());
        userRentACarService.rentACarByBookingCode(bookingCode);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
        rent = userRentACarService.getRentByPerson(currentPerson);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);

    }

    @Test
    public void bookACar() throws Exception {
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.bookACar(currentCar, currentPerson);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());
        booking = userRentACarService.getBookingByPerson(currentPerson);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
    }

    @Test
    public void returnACar() throws Exception {
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.rentACar(currentCar, person);
        rent = userRentACarService.getRentByPerson(person);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
        userRentACarService.returnACar(currentCar);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        currentRent = userRentACarService.getRentByPerson(person);
        assertNull(currentRent);
    }

    @Test
    public void cancelBooking() throws Exception {
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.bookACar(currentCar, person);
        booking = userRentACarService.getBookingByPerson(person);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());

        userRentACarService.cancelBooking(booking);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        currentBooking = userRentACarService.getBookingByPerson(person);
        assertNull(currentBooking);
    }

    @Test
    public void cancelBookingByCode() throws Exception {
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        String bookingCode = userRentACarService.bookACar(currentCar, person);
        assertNotNull(bookingCode);
        booking = userRentACarService.getBookingByPerson(person);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());

        userRentACarService.cancelBookingByCode(bookingCode);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        currentBooking = userRentACarService.getBookingByPerson(person);
        assertNull(currentBooking);
    }

}