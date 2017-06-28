package com.rentacar.test.service;

import com.rentacar.model.Login;
import com.rentacar.model.Person;
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
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    private Person person;
    private Person currentPerson;
    private Person expectedPerson;

    @Before
    public void setUp() throws ParseException {
        person = MockData.getMockPerson();
        currentPerson = person;
        expectedPerson = person;
    }

    @After
    public void cleanUp() {
        personService.deletePerson(currentPerson);
    }

    @Test
    public void findAllPersons() throws Exception {
        personService.savePerson(person);
        List<Person> personList = personService.findAllPersons();
        assertNotNull(personList);
        assertTrue(personList.size() >= 1);
    }

    @Test
    public void savePerson() throws Exception {
        currentPerson = personService.findById(person.getPersonId());
        assertNull(currentPerson);
        personService.savePerson(person);
        currentPerson = personService.findById(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

    @Test
    public void updatePerson() throws Exception {
        personService.savePerson(person);
        currentPerson.setLastName("Deer");
        currentPerson.setAddress("deer street");
        currentPerson.setPassword("test1234");
        expectedPerson = currentPerson;
        personService.updatePerson(currentPerson);
        currentPerson = personService.findById(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

    @Test
    public void deletePerson() throws Exception {
        personService.savePerson(person);
        currentPerson = personService.findById(person.getPersonId());
        assertNotNull(currentPerson);
        personService.deletePerson(person);
        currentPerson = personService.findById(person.getPersonId());
        assertNull(currentPerson);
    }

    @Test
    public void logIn() throws Exception {
        personService.savePerson(person);
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setPassword(person.getPassword());
        currentPerson = personService.logIn(login);
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

    @Test
    public void adminLogIn() throws Exception {
        personService.savePerson(person);
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setPassword(person.getPassword());
        expectedPerson = personService.adminLogIn(login);
        assertNull(expectedPerson);
    }

    @Test
    public void findByEmail() throws Exception {
        personService.savePerson(person);
        currentPerson = personService.findByEmail(person.getEmail());
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

}