package com.rentacar.services;

import com.rentacar.config.TestWebConfig;
import com.rentacar.model.Car;
import com.rentacar.model.Insurance;
import com.rentacar.model.Person;
import com.rentacar.testutils.TestDataUtil;
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
@ContextConfiguration(classes = TestWebConfig.class)
public class InsuranceServiceTest {
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private PersonService personService;
    @Autowired
    private CarService carService;

    private Insurance insurance;
    private Insurance currentInsurance;
    private Insurance expectedInsurance;
    private Car car;
    private Person person;

    @Before
    public void setUp() throws ParseException {
        insurance = TestDataUtil.getMockInsurance();
        car = TestDataUtil.getMockCar();
        person = TestDataUtil.getMockPerson();

        personService.savePerson(person);
        carService.saveCar(car);
        person = personService.findById(person.getPersonId());
        car = carService.findCarById(car.getCarId());
        insurance.setPerson(person);
        insurance.setCar(car);
    }

    @After
    public void cleanUp() {
        if (insurance != null)
            insuranceService.deleteInsurance(insurance);
        personService.deletePerson(person);
        carService.deleteCar(car);
    }

    @Test
    public void saveInsurance() throws Exception {
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNull(currentInsurance);
        insuranceService.saveInsurance(insurance);
        expectedInsurance = insurance;
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNotNull(currentInsurance);
        assertEquals(expectedInsurance, currentInsurance);
    }

    @Test
    public void findAllInsurances() throws Exception {
        insuranceService.saveInsurance(insurance);
        List<Insurance> insuranceList = insuranceService.findAllInsurances();
        assertNotNull(insuranceList);
        assertTrue(insuranceList.size() >= 1);
    }

    @Test
    public void updateInsurance() throws Exception {
        insuranceService.saveInsurance(insurance);
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNotNull(currentInsurance);
        currentInsurance.setCost(15.5);
        expectedInsurance = currentInsurance;
        insuranceService.updateInsurance(currentInsurance);
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNotNull(currentInsurance);
        assertEquals(expectedInsurance, currentInsurance);
    }

    @Test
    public void deleteInsurance() throws Exception {
        insuranceService.saveInsurance(insurance);
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNotNull(currentInsurance);
        insuranceService.deleteInsurance(currentInsurance);
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNull(currentInsurance);
    }
}