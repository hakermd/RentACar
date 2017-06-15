package com.rentacar.services;

import com.rentacar.dao.PersonDao;
import com.rentacar.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aplesca on 5/17/2017.
 */
public interface PersonService {
    public List<Person> findAllPersons();

    public void savePerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(Person person);
}
