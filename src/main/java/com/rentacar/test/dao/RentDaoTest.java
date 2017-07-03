package com.rentacar.test.dao;

import com.rentacar.dao.CarDao;
import com.rentacar.dao.InsuranceDao;
import com.rentacar.dao.PersonDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.Car;
import com.rentacar.model.Insurance;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
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
public class RentDaoTest {

    private Session session;
    private RentDao rentDao;
    private InsuranceDao insuranceDao;
    private CarDao carDao;
    private PersonDao personDao;

    private Insurance insurance;
    private Person person;
    private Car car;

    private Rent rent;
    private Rent currentRent;

    @Autowired
    private void setRentDao(RentDao rentDao) {
        this.rentDao = rentDao;
    }

    @Autowired
    private void setInsuranceDao(InsuranceDao insuranceDao) {
        this.insuranceDao = insuranceDao;
    }

    @Autowired
    private void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Autowired
    private void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }

    @Before
    public void setUp() throws ParseException {
        session = insuranceDao.getSessionFactory().getCurrentSession();
        insurance = MockData.getMockInsurance();
        car = MockData.getMockCar();
        person = MockData.getMockPerson();
        rent = MockData.getMockRent();

        personDao.save(person);
        carDao.save(car);
        session.getTransaction().commit();
        session.getTransaction().begin();
        insurance.setPerson(person);
        insurance.setCar(car);
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        rent.setPerson(person);
        rent.setCar(car);
        rent.setInsurance(insurance);
        rent.setCost(car.getCarPrice() + insurance.getCost());
    }

    @After
    public void cleanUp() throws ParseException {
        if (!session.getTransaction().isActive()) {
            session.getTransaction().begin();
        }
        rentDao.delete(currentRent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        insuranceDao.delete(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        personDao.delete(person);
        carDao.delete(car);
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void findRentById() throws Exception {
        session.getTransaction().begin();
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);

    }

    @Test
    public void findAllRents() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
        List<Rent> rents = rentDao.findAll();
        assertNotNull(rents);
        assertTrue(rents.size() >= 1);

    }

    @Test
    public void addRent() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
    }

    @Test
    public void updateRent() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
        currentRent.setCost(150);
        rentDao.update(currentRent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);

    }

    @Test
    public void deleteRent() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
        rentDao.delete(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
    }

    @Test
    public void deleteRentById() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
        rentDao.deleteById(rent.getRentId());
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
    }

    @Test
    public void getRentByCar() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.getRentByCar(car);
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
    }

    @Test
    public void getRentByPerson() throws Exception {
        session.getTransaction().begin();
        currentRent = rentDao.findOne(rent.getRentId());
        assertNull(currentRent);
        rentDao.save(rent);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentRent = rentDao.getRentByPerson(person);
        assertNotNull(currentRent);
        assertEquals(rent, currentRent);
    }

}