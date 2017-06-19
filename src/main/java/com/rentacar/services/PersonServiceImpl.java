package com.rentacar.services;

import antlr.StringUtils;
import com.rentacar.dao.PersonDao;
import com.rentacar.model.Car;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Person logIn(Login login) {
        return personDao.logIn(login.getEmail(), login.getPassword());
    }
}