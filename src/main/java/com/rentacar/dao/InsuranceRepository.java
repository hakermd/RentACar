package com.rentacar.dao;

import com.rentacar.model.Insurance;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Andrei.Plesca
 */

public interface InsuranceRepository extends CrudRepository<Insurance, Long> {
}
