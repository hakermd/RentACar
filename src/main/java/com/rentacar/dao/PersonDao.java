package com.rentacar.dao;

import com.rentacar.model.Person;

/**
 * Created by Andrei.Plesca
 */
public interface PersonDao extends DAO<Person> {
    Person logIn(String email, String password);

    Person adminLogIn(String email, String password);

    Person findByEmail(String email);
}
