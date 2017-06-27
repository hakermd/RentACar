package com.rentacar.services;

import com.rentacar.dao.CarDao;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
import com.rentacar.model.enums.EconomyClass;
import com.rentacar.model.enums.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public Car findCarByWinCode(String carWinCode) {
        return carDao.findCarByWinCode(carWinCode);
    }

    @Override
    public Car findCarByRegistrationNumber(String registrationNumber) {
        return carDao.findCarByRegistrationNumber(registrationNumber);
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveCar(Car car) {
        carDao.save(car);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateCar(Car car) {
        carDao.update(car);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void deleteCar(Car car) {
        carDao.delete(car);
    }
}
