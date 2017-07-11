package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface CarDao extends DAO<Car> {

    List<Car> searchACarByStatus(CarAvailability carAvailability);

    List searchACarByCriteria(CarFilter filter);

    Car findCarByWinCode(String carWinCode);

    Car findCarByRegistrationNumber(String registrationNumber);
}
