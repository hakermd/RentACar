package com.rentacar.services;

import com.rentacar.dao.InsuranceDao;
import com.rentacar.model.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Service
public class InsuranceServiceImpl implements InsuranceService {
    @Autowired
    private InsuranceDao insuranceDao;

    @Override
    public List<Insurance> findAllInsurances() {
        List<Insurance> persons = insuranceDao.findAll();
        for (Insurance i : persons) {
            System.out.println("-----------------" + i.getCar().getModel());
        }
        return persons;
    }

    @Override
    public void saveInsurance(Insurance insurance) {
        insuranceDao.save(insurance);
    }

    @Override
    public void updateInsurance(Insurance insurance) {
        insuranceDao.update(insurance);
    }

    @Override
    public void deleteInsurance(Insurance insurance) {
        insuranceDao.delete(insurance);
    }
}
