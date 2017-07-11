package com.rentacar.services;

import com.rentacar.model.Insurance;

import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface InsuranceService {
    Insurance findInsuranceById(long id);

    List<Insurance> findAllInsurances();

    void saveInsurance(Insurance insurance);

    void updateInsurance(Insurance insurance);

    void deleteInsurance(Insurance insurance);
}
