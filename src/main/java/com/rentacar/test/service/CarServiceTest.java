package com.rentacar.test.service;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.*;
import com.rentacar.services.CarService;
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
public class CarServiceTest {
    @Autowired
    private CarService carService;

    private Car car;
    private Car currentCar;
    private Car expectedCar;

    @Before
    public void setUp() throws ParseException {
        car = TestDataUtil.getMockCar();
        currentCar = car;
        expectedCar = car;
    }

    @After
    public void cleanUp() {
        carService.deleteCar(currentCar);
    }

    @Test
    public void saveCar() throws Exception {
        currentCar = carService.findCarById(car.getCarId());
        assertNull(currentCar);
        carService.saveCar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(expectedCar, currentCar);
    }

    @Test
    public void updateCar() throws Exception {
        carService.saveCar(car);
        currentCar.setTransmission(CarTransmission.MANUAL);
        currentCar.setCarPrice(100.0);
        expectedCar = currentCar;
        carService.updateCar(currentCar);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        assertEquals(expectedCar, currentCar);
    }

    @Test
    public void findCarByWinCode() throws Exception {
        carService.saveCar(car);
        currentCar = carService.findCarByWinCode(car.getWinCode());
        assertNotNull(currentCar);
        assertEquals(expectedCar, currentCar);
    }

    @Test
    public void findCarByRegistrationNumber() throws Exception {
        carService.saveCar(car);
        currentCar = carService.findCarByRegistrationNumber(car.getRegistrationNumber());
        assertNotNull(currentCar);
        assertEquals(expectedCar, currentCar);
    }

    @Test
    public void showAllAvailableCars() throws Exception {
        carService.saveCar(car);
        List<Car> carList = carService.showAllAvailableCars();
        assertNotNull(carList);
        for (Car c : carList) {
            assertEquals(CarAvailability.AVAILABLE, c.getAvailability());
        }
    }

    @Test
    public void filterCars() throws Exception {
        CarFilter carFilter = new CarFilter();
        carFilter.setEconomyClass(EconomyClass.PREMIUM);
        carFilter.setCarType(CarType.COUPE);
        carFilter.setOptions(Options.PREMIUM);
        carFilter.setTransmission(CarTransmission.AUTOMATIC);
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        carFilter.setYearOfProduction("2012");

        carService.saveCar(car);
        List<Car> carList = carService.filterCars(carFilter);
        assertNotNull(carList);
        for (Car c : carList) {
            assertEquals(carFilter.getEconomyClass(), c.getEconomyClass());
            assertEquals(carFilter.getCarType(), c.getType());
            assertEquals(carFilter.getOptions(), c.getOptions());
            assertEquals(carFilter.getTransmission(), c.getTransmission());
            assertEquals(carFilter.getCarAvailability(), c.getAvailability());
            assertEquals(carFilter.getYearOfProduction(), c.getYearOfProduction());
        }
    }

    @Test
    public void showAllCars() throws Exception {
        carService.saveCar(car);
        List<Car> carList = carService.showAllCars();
        assertNotNull(carList);
        assertTrue(carList.size() >= 1);
    }


    @Test
    public void deleteCar() throws Exception {
        carService.saveCar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNotNull(currentCar);
        carService.deleteCar(car);
        currentCar = carService.findCarById(car.getCarId());
        assertNull(currentCar);
    }

}