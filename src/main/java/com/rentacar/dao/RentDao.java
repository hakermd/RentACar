package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;

/**
 * Created by Andrei.Plesca
 */
public interface RentDao extends DAO<Rent> {
    Rent getRentByCar(Car car);

    Rent getRentByPerson(Person person);
}
