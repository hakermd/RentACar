package com.rentacar.services;

import com.rentacar.dao.InsuranceDao;
import com.rentacar.model.Car;
import com.rentacar.model.Insurance;
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
public class InsuranceServiceImpl implements InsuranceService {
    @Autowired
    private InsuranceDao insuranceDao;

    @Override
    public Insurance findInsuranceById(long id) {
        return insuranceDao.findOne(id);
    }

    @Override
    public List<Insurance> findAllInsurances() {
        return insuranceDao.findAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveInsurance(Insurance insurance) {
        insuranceDao.save(insurance);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateInsurance(Insurance insurance) {
        insuranceDao.update(insurance);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void deleteInsurance(Insurance insurance) {
        insuranceDao.delete(insurance);
    }
}
