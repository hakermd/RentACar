package com.rentacar.dao;

import com.rentacar.model.Insurance;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aplesca on 5/17/2017.
 */

public interface InsuranceDao extends DAO<Insurance> {
}
