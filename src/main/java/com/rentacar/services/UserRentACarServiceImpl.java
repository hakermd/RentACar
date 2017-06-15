package com.rentacar.services;

import com.rentacar.dao.CarDao;
import com.rentacar.dao.InsuranceDao;
import com.rentacar.dao.PersonDao;
import com.rentacar.dao.RentDao;
import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.Rent;
import com.rentacar.model.enums.CarAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aplesca on 5/8/2017.
 */
@Service
public class UserRentACarServiceImpl implements UserRentACarService {
    @Autowired
    private CarDao carDao;
    @Autowired
    private RentDao rentDao;

    public Car findCarByWinCode(String carWinCode) {
        return carDao.findCarByWinCode(carWinCode);
    }

    public List<Car> showAllCars() {
        return carDao.findAll();
    }

    public List<Car> showAllAvailableCars() {
        return carDao.searchACarByStatus(CarAvailability.AVAILABLE);
    }

    public List<Car> searchACar(CarFilter filter) {
        return carDao.searchACarByCriteria(filter);
    }

    public void rentACar(Rent rent) {
        rentDao.save(rent);
    }

    public Car rentACarByBookingId(int bookingId) {
        return null;
    }

    public int bookACar(Car car) {
        return 0;
    }

    public void returnACar(Car car) {

    }

    public void cancelBooking(int bookingId) {

    }
}
