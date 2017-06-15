package com.rentacar.services;

import com.rentacar.dao.CarDao;
import com.rentacar.model.Car;
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
    public List<Car> findAllCars() {
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
