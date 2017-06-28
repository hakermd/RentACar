package com.rentacar.test.dao;

import com.rentacar.dao.PersonDao;
import com.rentacar.model.DrivingLicense;
import com.rentacar.model.Person;
import com.rentacar.model.enums.Gender;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created by Andrei.Plesca
 */
public class PersonDaoTest {

    @Autowired
    private PersonDao personDao;

    @Before
    private void createPerson() throws ParseException {

    }

    @Test
    public void findPersonById() throws Exception {
    }

    @Test
    public void findAllPersons() throws Exception {
    }

    @Test
    public void addPerson() throws Exception {
    }

    @Test
    public void updatePerson() throws Exception {
    }

    @Test
    public void deletePerson() throws Exception {
    }

    @Test
    public void deletePersonById() throws Exception {
    }

    @Test
    public void logIn() throws Exception {
    }

    @Test
    public void adminLogIn() throws Exception {
    }

    @Test
    public void findByEmail() throws Exception {
    }

}