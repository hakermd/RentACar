package com.rentacar.test.service;

import com.rentacar.model.*;
import com.rentacar.model.enums.*;
import com.rentacar.services.AdminRentACarService;
import com.rentacar.services.CarService;
import com.rentacar.services.PersonService;
import com.rentacar.services.UserRentACarService;
import com.rentacar.test.testutils.TestDataUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Andrei.Plesca
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
public class AdminRentACarServiceTest {
    @Autowired
    private AdminRentACarService adminRentACarService;
    @Autowired
    private CarService carService;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserRentACarService userRentACarService;

    private Car car;
    private Car currentCar;
    private Person person;
    private Rent rent;
    private Booking booking;

    @Before
    public void setUp() throws ParseException {
        car = TestDataUtil.getMockCar();
        person = TestDataUtil.getMockPerson();
        personService.savePerson(person);
    }

    @After
    public void cleanUp() {
        if (rent != null) {
            userRentACarService.deleteRent(rent);
        }
        if (booking != null) {
            userRentACarService.deleteBooking(booking);
        }
        personService.deletePerson(person);
        carService.deleteCar(currentCar);
    }

    @Test
    public void addACar() throws Exception {
        currentCar = carService.findCarById(car.getCarId());
        assertNull(currentCar);
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

    @Test
    public void removeACar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        adminRentACarService.removeACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNull(currentCar);
    }

    @Test
    public void suspendACar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(currentCar.getAvailability(), CarAvailability.AVAILABLE);
        adminRentACarService.suspendACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(currentCar.getAvailability(), CarAvailability.BROKEN);
    }

    @Test
    public void unsuspendACar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(currentCar.getAvailability(), CarAvailability.AVAILABLE);
        adminRentACarService.suspendACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(currentCar.getAvailability(), CarAvailability.BROKEN);
        adminRentACarService.unsuspendACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(currentCar.getAvailability(), CarAvailability.AVAILABLE);
    }

    @Test
    public void getAllCars() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        List<Car> carList = adminRentACarService.getAllCars();
        assertNotNull(carList);
        assertTrue(carList.size() >= 1);
    }

    @Test
    public void searchACar() throws Exception {
        CarFilter carFilter = new CarFilter();
        carFilter.setEconomyClass(EconomyClass.PREMIUM);
        carFilter.setCarType(CarType.COUPE);
        carFilter.setOptions(Options.PREMIUM);
        carFilter.setTransmission(CarTransmission.AUTOMATIC);
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        carFilter.setYearOfProduction("2012");
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        List<Car> carList = adminRentACarService.searchACar(carFilter);
        assertNotNull(carList);
        for (Car c : carList) {
            assertEquals(carFilter.getEconomyClass(), c.getEconomyClass());
            assertEquals(carFilter.getCarType(), c.getType());
            assertEquals(carFilter.getTransmission(), c.getTransmission());
            assertEquals(carFilter.getOptions(), c.getOptions());
            assertEquals(carFilter.getCarAvailability(), c.getAvailability());
            assertEquals(carFilter.getYearOfProduction(), c.getYearOfProduction());
        }
    }

    @Test
    public void changeACarInfo() throws Exception {
        adminRentACarService.addACar(car);
        car.setTransmission(CarTransmission.MANUAL);
        car.setCarPrice(100.0);
        adminRentACarService.changeACarInfo(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

    @Test
    public void searchACarByStatus() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        List<Car> carList = adminRentACarService.searchACarByStatus(CarAvailability.AVAILABLE);
        assertNotNull(carList);
        for (Car c : carList) {
            assertEquals(CarAvailability.AVAILABLE, c.getAvailability());
        }
    }

    @Test
    public void cancelRent() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.rentACar(currentCar, person);
        rent = userRentACarService.getRentByPerson(person);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
        adminRentACarService.cancelRent(rent);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        assertNull(userRentACarService.getRentByPerson(person));
    }

    @Test
    public void cancelRentByPerson() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.rentACar(currentCar, person);
        rent = userRentACarService.getRentByPerson(person);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
        adminRentACarService.cancelRentByPerson(person);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        assertNull(userRentACarService.getRentByPerson(person));
    }

    @Test
    public void cancelRentByCar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.rentACar(currentCar, person);
        rent = userRentACarService.getRentByPerson(person);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
        adminRentACarService.cancelRentByCar(car);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        assertNull(userRentACarService.getRentByPerson(person));
    }

    @Test
    public void cancelBooking() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.bookACar(currentCar, person);
        booking = userRentACarService.getBookingByPerson(person);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());
        adminRentACarService.cancelBooking(booking);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        assertNull(userRentACarService.getBookingByPerson(person));
    }

    @Test
    public void cancelBookingByPerson() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.bookACar(currentCar, person);
        booking = userRentACarService.getBookingByPerson(person);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());
        adminRentACarService.cancelBookingByPerson(person);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        assertNull(userRentACarService.getBookingByPerson(person));
    }

    @Test
    public void cancelBookingByCar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.bookACar(currentCar, person);
        booking = userRentACarService.getBookingByPerson(person);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());
        adminRentACarService.cancelBookingByCar(car);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        assertNull(userRentACarService.getBookingByPerson(person));
    }

    @Test
    public void getRentByCar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.rentACar(currentCar, person);
        rent = userRentACarService.getRentByPerson(person);
        assertNotNull(rent);
        assertEquals(rent.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.RENTED, currentCar.getAvailability());
    }

    @Test
    public void getBookingByCar() throws Exception {
        adminRentACarService.addACar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertEquals(CarAvailability.AVAILABLE, currentCar.getAvailability());
        userRentACarService.bookACar(currentCar, person);
        booking = userRentACarService.getBookingByPerson(person);
        assertNotNull(booking);
        assertEquals(booking.isActive(), true);
        currentCar = carService.findCarById(currentCar.getCarId());
        assertEquals(CarAvailability.BOOKED, currentCar.getAvailability());
    }

}