package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Andrei.Plesca
 */
public interface RentRepository extends CrudRepository<Rent, Long> {
    Rent getRentByCarAndActiveTrue(Car car);

    Rent getRentByPersonAndActiveTrue(Person person);
}
