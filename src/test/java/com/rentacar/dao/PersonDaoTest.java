package com.rentacar.dao;

import com.rentacar.model.Person;
import com.rentacar.testutils.TestDataUtil;
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
public class PersonDaoTest {

    private Session session;
    private Person person;
    private Person currentPerson;

    private PersonDao personDao;

    @Autowired
    private void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Before
    public void setUp() throws ParseException {
        session = personDao.getSessionFactory().getCurrentSession();
        person = TestDataUtil.getMockPerson();
        currentPerson = person;
    }

    @After
    public void cleanUp() throws ParseException {
        if (!session.getTransaction().isActive()) {
            session.getTransaction().begin();
        }
        personDao.delete(currentPerson);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void createPerson() throws ParseException {
        currentPerson = personDao.findOne(person.getPersonId());
        assertNull(currentPerson);
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
    }

    @Test
    public void findPersonById() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
    }

    @Test
    public void findAllPersons() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
        List<Person> personList = personDao.findAll();
        assertNotNull(personList);
        assertTrue(personList.size() >= 1);
    }

    @Test
    public void updatePerson() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
        person.setAddress("Test");
        person.setLastName("Test");
        personDao.update(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
    }

    @Test
    public void deletePerson() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
        personDao.delete(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNull(currentPerson);
    }

    @Test
    public void deletePersonById() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
        personDao.deleteById(person.getPersonId());
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNull(currentPerson);
    }

    @Test
    public void logIn() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findOne(person.getPersonId());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
        currentPerson = personDao.logIn(person.getEmail(), person.getPassword());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
    }

    @Test
    public void findByEmail() throws Exception {
        personDao.save(person);
        session.getTransaction().commit();
        session.getTransaction().begin();
        currentPerson = personDao.findByEmail(person.getEmail());
        assertNotNull(currentPerson);
        assertEquals(person, currentPerson);
    }

}