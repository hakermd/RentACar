package com.rentacar.test.service;

import com.rentacar.model.Car;
import com.rentacar.model.Insurance;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
import com.rentacar.services.CarService;
import com.rentacar.services.InsuranceService;
import com.rentacar.services.PersonService;
import com.rentacar.test.mockdata.MockData;
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
    private Car currentCar;
    private Person currentPerson;

    @Before
    public void setUp() throws ParseException {
        insurance = MockData.getMockInsurance();
        car = MockData.getMockCar();
        person = MockData.getMockPerson();
        currentInsurance = insurance;
        expectedInsurance = insurance;

        personService.savePerson(person);
        carService.saveCar(car);
        currentPerson = personService.findById(person.getPersonId());
        currentCar = carService.findCarById(car.getCarId());
        insurance.setPerson(currentPerson);
        insurance.setCar(currentCar);
    }

    @After
    public void cleanUp() {
        insuranceService.deleteInsurance(currentInsurance);
        personService.deletePerson(currentPerson);
        carService.deleteCar(currentCar);
    }

    @Test
    public void findAllInsurances() throws Exception {
        insuranceService.saveInsurance(insurance);
        List<Insurance> insuranceList = insuranceService.findAllInsurances();
        assertNotNull(insuranceList);
        assertTrue(insuranceList.size() >= 1);
    }

    @Test
    public void saveInsurance() throws Exception {
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNull(currentInsurance);
        insuranceService.saveInsurance(insurance);
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNotNull(currentInsurance);
        assertEquals(expectedInsurance, currentInsurance);
    }

    @Test
    public void updateInsurance() throws Exception {
        insuranceService.saveInsurance(insurance);
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
        insuranceService.deleteInsurance(insurance);
        currentInsurance = insuranceService.findInsuranceById(insurance.getId());
        assertNull(currentInsurance);
    }
}