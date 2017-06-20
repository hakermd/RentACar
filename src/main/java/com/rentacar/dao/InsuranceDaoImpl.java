package com.rentacar.dao;

import com.rentacar.model.Insurance;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrei.Plesca
 */
@Repository
public class InsuranceDaoImpl extends AbstractHibernateDAO<Insurance> implements InsuranceDao {
    protected InsuranceDaoImpl() {
        super(Insurance.class);
    }
}
