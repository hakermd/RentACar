package com.rentacar.services;

import com.rentacar.model.Insurance;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface InsuranceService {
    public List<Insurance> findAllInsurances();

    public void saveInsurance(Insurance insurance);

    public void updateInsurance(Insurance insurance);

    public void deleteInsurance(Insurance insurance);
}
