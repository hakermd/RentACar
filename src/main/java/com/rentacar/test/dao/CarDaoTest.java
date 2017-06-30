package com.rentacar.test.dao;

import com.rentacar.dao.CarDao;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.*;
import com.rentacar.test.mockdata.MockData;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by Andrei.Plesca
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class CarDaoTest {

    private Session session;
    private Car car;
    private Car currentCar;

    private CarDao carDao;

    @Autowired
    private void setCarDao(CarDao carDao) {
        this.carDao = carDao;

    }

    @Before
    public void setUp() throws ParseException {
        car = MockData.getMockCar();
        currentCar = car;
        session = carDao.getSessionFactory().getCurrentSession();
    }

    @After
    public void cleanUp() throws ParseException {
        if (!session.getTransaction().isActive()) {
            session.getTransaction().begin();
        }
        carDao.delete(currentCar);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void addCar() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

    @Test
    public void findCarById() throws Exception {
        currentCar = carDao.findOne(car.getCarId());
        assertNull(currentCar);
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

    @Test
    public void findAllCars() throws Exception {
        carDao.save(car);
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        session.getTransaction().begin();
        List<Car> carList = carDao.findAll();
        session.getTransaction().commit();
        assertNotNull(carList);
        assertTrue(carList.size() >= 1);
    }

    @Test
    public void updateCar() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(car);
        assertEquals(car, currentCar);
        car.setCarPrice(100);
        car.setFuelType(FuelType.DIESEL);
        car.setOptions(Options.FULL);
        session.getTransaction().begin();
        carDao.update(car);
        session.getTransaction().commit();
        currentCar = carDao.findOne(currentCar.getCarId());
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

    @Test
    public void deleteCar() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
        session.getTransaction().begin();
        carDao.delete(currentCar);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNull(currentCar);
    }

    @Test
    public void deleteCarById() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
        session.getTransaction().begin();
        carDao.deleteById(currentCar.getCarId());
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNull(currentCar);
    }

    @Test
    public void searchACarByStatus() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
        session.getTransaction().begin();
        List<Car> carList = carDao.searchACarByStatus(car.getAvailability());
        session.getTransaction().commit();
        assertNotNull(carList);
        for (Car c : carList) {
            assertEquals(car.getAvailability(), c.getAvailability());
        }
    }

    @Test
    public void searchACarByCriteria() throws Exception {
        CarFilter carFilter = new CarFilter();
        carFilter.setEconomyClass(EconomyClass.PREMIUM);
        carFilter.setCarType(CarType.COUPE);
        carFilter.setOptions(Options.PREMIUM);
        carFilter.setTransmission(CarTransmission.AUTOMATIC);
        carFilter.setCarAvailability(CarAvailability.AVAILABLE);
        carFilter.setYearOfProduction("2012");

        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
        session.getTransaction().begin();
        List<Car> carList = carDao.searchACarByStatus(car.getAvailability());
        session.getTransaction().commit();
        assertNotNull(carList);
        for (Car c : carList) {
            assertEquals(carFilter.getCarType(), c.getType());
            assertEquals(carFilter.getEconomyClass(), c.getEconomyClass());
            assertEquals(carFilter.getOptions(), c.getOptions());
            assertEquals(carFilter.getTransmission(), c.getTransmission());
            assertEquals(carFilter.getCarAvailability(), c.getAvailability());
            assertEquals(carFilter.getYearOfProduction(), c.getYearOfProduction());
        }
    }

    @Test
    public void findCarByWinCode() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
        session.getTransaction().begin();
        currentCar = carDao.findCarByWinCode(car.getWinCode());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

    @Test
    public void findCarByRegistrationNumber() throws Exception {
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentCar = carDao.findOne(car.getCarId());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
        session.getTransaction().begin();
        currentCar = carDao.findCarByRegistrationNumber(car.getRegistrationNumber());
        session.getTransaction().commit();
        assertNotNull(currentCar);
        assertEquals(car, currentCar);
    }

}