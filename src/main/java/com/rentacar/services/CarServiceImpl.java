package com.rentacar.services;

import com.rentacar.dao.CarDao;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public Car findCarByWinCode(String carWinCode) {
        return carDao.findCarByWinCode(carWinCode);
    }

    @Override
    public List<Car> showAllAvailableCars() {
        return carDao.searchACarByStatus(CarAvailability.AVAILABLE);
    }

    @Override
    public List<Car> filterCars(CarFilter filter) {
        return carDao.searchACarByCriteria(filter);
    }

    @Override
    public List<Car> showAllCars() {
        return carDao.findAll();
    }

    @Override
    public void saveCar(Car car) {
        carDao.save(car);
    }

    @Override
    public void updateCar(Car car) {
        carDao.update(car);
    }

    @Override
    public void deleteCar(Car car) {
        carDao.delete(car);
    }
}
