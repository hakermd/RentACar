package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;

import java.util.List;

/**
 * Created by aplesca on 5/8/2017.
 */
public interface CarDao extends DAO<Car> {

    public List<Car> searchACarByStatus(CarAvailability carAvailability);

    public List<Car> searchACarByCriteria(CarFilter filter);

    public Car findCarByWinCode(String carWinCode);
}
