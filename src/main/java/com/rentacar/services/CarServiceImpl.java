package com.rentacar.services;

import com.rentacar.dao.CarRepository;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
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
    private CarRepository carRepository;

    @Override
    public Car findCarById(long carId) {
        return carRepository.findOne(carId);
    }

    @Override
    public Car findCarByWinCode(String carWinCode) {
        return carRepository.findCarByWinCode(carWinCode);
    }

    @Override
    public Car findCarByRegistrationNumber(String registrationNumber) {
        return carRepository.findCarByRegistrationNumber(registrationNumber);
    }

    @Override
    public List<Car> showAllAvailableCars() {
        return carRepository.findACarByAvailability(CarAvailability.AVAILABLE);
    }

    @Override
    public List<Car> filterCars(CarFilter filter) {
        return carRepository.findACarByType(filter.getCarType());
    }

    @Override
    public List<Car> showAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCar(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCar(Car car) {
        carRepository.delete(car);
    }
}
