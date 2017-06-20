package com.rentacar.services;

import com.rentacar.dao.PersonDao;
import com.rentacar.model.Login;
import com.rentacar.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;

    @Override
    public List<Person> findAllPersons() {
        return personDao.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void savePerson(Person person) {
        personDao.save(person);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updatePerson(Person person) {
        personDao.update(person);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void deletePerson(Person person) {
        personDao.delete(person);
    }

    @Override
    public Person logIn(Login login) {
        return personDao.logIn(login.getEmail(), login.getPassword());
    }
}