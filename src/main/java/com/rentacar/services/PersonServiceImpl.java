package com.rentacar.services;

import com.rentacar.dao.PersonDao;
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
        List<Person> persons = personDao.findAll();
        for (Person p : persons) {
            System.out.println("-----------------" + p.getFirstName());
        }
        return persons;
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
}