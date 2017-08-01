package com.rentacar.services;

import com.rentacar.config.TestWebConfig;
import com.rentacar.model.Login;
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
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    private Person person;
    private Person currentPerson;
    private Person expectedPerson;

    @Before
    public void setUp() throws ParseException {
        person = TestDataUtil.getMockPerson();
    }

    @After
    public void cleanUp() {
        if (person != null)
            personService.deletePerson(person);
    }

    @Test
    public void savePerson() throws Exception {
        currentPerson = personService.findByEmail(person.getEmail());
        assertNull(currentPerson);
        personService.savePerson(person);
        expectedPerson = person;
        currentPerson = personService.findById(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

    @Test
    public void findAllPersons() throws Exception {
        personService.savePerson(person);
        List<Person> personList = personService.findAllPersons();
        assertNotNull(personList);
        assertTrue(personList.size() >= 1);
    }

    @Test
    public void updatePerson() throws Exception {
        personService.savePerson(person);
        currentPerson = person;
        currentPerson.setLastName("Deer");
        currentPerson.setAddress("deer street");
        currentPerson.setUserPassword("test1234");
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
        expectedPerson = person;
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setUserPassword(person.getUserPassword());
        currentPerson = personService.logIn(login);
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

    @Test
    public void adminLogIn() throws Exception {
        personService.savePerson(person);
        Login login = new Login();
        login.setEmail(person.getEmail());
        login.setUserPassword(person.getUserPassword());
        expectedPerson = personService.adminLogIn(login);
        assertNull(expectedPerson);
    }

    @Test
    public void findByEmail() throws Exception {
        personService.savePerson(person);
        expectedPerson = person;
        currentPerson = personService.findByEmail(person.getEmail());
        assertNotNull(currentPerson);
        assertEquals(expectedPerson, currentPerson);
    }

}