package com.rentacar.dao;

import com.rentacar.model.Person;

/**
 * Created by Andrei.Plesca
 */
public interface PersonDao extends DAO<Person> {
    public Person logIn(String email, String password);
}
