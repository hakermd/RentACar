package com.rentacar.test.dao;

import com.rentacar.dao.CarDao;
import com.rentacar.model.Car;
import com.rentacar.test.mockdata.MockData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Andrei.Plesca
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
//@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CarDaoTest {
    private Car expectedCar;

    private CarDao carDao;

    @Autowired
    private void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }

    @Before
    public void init() throws ParseException {
        expectedCar = MockData.getMockCar();
    }

    @Test
    public void addCar() throws Exception {
        carDao.save(expectedCar);
        Car actualCar = carDao.findOne(1L);
        assertEquals(actualCar, expectedCar);
    }

    @Test
    public void findCarById() throws Exception {
        assertTrue(true);
    }

    @Test
    public void findAllCars() throws Exception {
    }

    @Test
    public void updateCar() throws Exception {
    }

    @Test
    public void deleteCar() throws Exception {
    }

    @Test
    public void deleteCarById() throws Exception {
    }

    @Test
    public void searchACarByStatus() throws Exception {
    }

    @Test
    public void searchACarByCriteria() throws Exception {
    }

    @Test
    public void findCarByWinCode() throws Exception {
    }

    @Test
    public void findCarByRegistrationNumber() throws Exception {
    }

}