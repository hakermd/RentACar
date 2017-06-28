package com.rentacar.services;

import com.rentacar.model.Login;
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

    public Person logIn(Login login);

    public Person adminLogIn(Login login);

    public Person findByEmail(String email);

    public Person findById(long id);
}
