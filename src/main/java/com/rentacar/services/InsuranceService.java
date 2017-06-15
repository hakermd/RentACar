package com.rentacar.services;

import com.rentacar.dao.InsuranceDao;
import com.rentacar.model.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InsuranceService {
    public List<Insurance> findAllInsurances();

    public void saveInsurance(Insurance insurance);

    public void updateInsurance(Insurance insurance);

    public void deleteInsurance(Insurance insurance);
}
