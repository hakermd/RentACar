package com.rentacar.services;

import com.rentacar.model.Login;
import com.rentacar.model.Person;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface PersonService {
    List<Person> findAllPersons();

    void savePerson(Person person);

    void updatePerson(Person person);

    void deletePerson(Person person);

    Person logIn(Login login);

    Person adminLogIn(Login login);

    Person findByEmail(String email);

    Person findByLicenseNumber(String licenseNumber);

    Person findById(long id);
}
