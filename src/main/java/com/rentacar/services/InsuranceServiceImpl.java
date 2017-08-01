package com.rentacar.services;

import com.rentacar.dao.InsuranceRepository;
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
    private InsuranceRepository insuranceRepository;

    @Override
    public Insurance findInsuranceById(long id) {
        return insuranceRepository.findOne(id);
    }

    @Override
    public List<Insurance> findAllInsurances() {
        return (List<Insurance>) insuranceRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveInsurance(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateInsurance(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteInsurance(Insurance insurance) {
        insuranceRepository.delete(insurance);
    }
}
