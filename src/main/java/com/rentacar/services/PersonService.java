package com.rentacar.services;

import com.rentacar.model.Person;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface PersonService {
    public List<Person> findAllPersons();

    public void savePerson(Person person);

    public void updatePerson(Person person);

    public void deletePerson(Person person);
}
