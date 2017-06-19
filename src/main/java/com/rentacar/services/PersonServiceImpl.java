package com.rentacar.services;

import com.rentacar.dao.PersonDao;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Override
    public List<Person> findAllPersons() {
        return  personDao.findAll();
    }

    @Override
    public void savePerson(Person person) {
        personDao.save(person);
    }

    @Override
    public void updatePerson(Person person) {
        personDao.update(person);
    }

    @Override
    public void deletePerson(Person person) {
        personDao.delete(person);
    }

    @Override
    public Person logIn(Login login) {
        return personDao.logIn(login.getEmail(), login.getPassword());
    }
}