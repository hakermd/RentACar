package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.Person;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.model.enums.CarType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findACarByAvailability(CarAvailability availability);

    Car findCarByWinCode(String winCode);

    Car findCarByRegistrationNumber(String registrationNumber);
    //TODO now is stub method for criteria
    List<Car> findACarByType(CarType filter);
}
