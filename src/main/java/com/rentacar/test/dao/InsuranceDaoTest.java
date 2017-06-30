package com.rentacar.test.dao;

import com.rentacar.dao.CarDao;
import com.rentacar.dao.InsuranceDao;
import com.rentacar.dao.PersonDao;
import com.rentacar.model.Car;
import com.rentacar.model.Insurance;
import com.rentacar.model.Person;
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

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/application-context.xml")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class InsuranceDaoTest {

    private Session session;
    private Insurance insurance;
    private Insurance currentInsurance;

    private Person person;
    private Car car;

    private InsuranceDao insuranceDao;
    private CarDao carDao;
    private PersonDao personDao;

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
        personDao.save(person);
        carDao.save(car);
        currentInsurance = insurance;
        session.getTransaction().commit();
        insurance.setPerson(person);
        insurance.setCar(car);
    }

    @After
    public void cleanUp() throws ParseException {
        if (!session.getTransaction().isActive()) {
            session.getTransaction().begin();
        }
        insuranceDao.delete(currentInsurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        personDao.delete(person);
        carDao.delete(car);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void findInsuranceById() throws Exception {
        session.getTransaction().begin();
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
    }

    @Test
    public void findAllInsurances() throws Exception {
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        assertNull(currentInsurance);
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
        List<Insurance> insuranceList = insuranceDao.findAll();
        assertNotNull(insuranceList);
        assertTrue(insuranceList.size() >= 0);
    }

    @Test
    public void addInsurance() throws Exception {
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        assertNull(currentInsurance);
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
    }

    @Test
    public void updateInsurance() throws Exception {
        session.getTransaction().begin();
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
        insurance.setCost(20);
        session.getTransaction().begin();
        insuranceDao.update(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
    }

    @Test
    public void deleteInsurance() throws Exception {
        session.getTransaction().begin();
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
        session.getTransaction().begin();
        insuranceDao.delete(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
    }

    @Test
    public void deleteInsuranceById() throws Exception {
        session.getTransaction().begin();
        insuranceDao.save(insurance);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
        assertNotNull(currentInsurance);
        assertEquals(insurance, currentInsurance);
        session.getTransaction().begin();
        insuranceDao.deleteById(insurance.getId());
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentInsurance = insuranceDao.findOne(insurance.getId());
        session.getTransaction().commit();
    }
}