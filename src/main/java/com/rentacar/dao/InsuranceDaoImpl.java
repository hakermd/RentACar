package com.rentacar.dao;

import com.rentacar.model.Insurance;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrei.Plesca
 */
@Repository
public class InsuranceDaoImpl extends AbstractHibernateDAO<Insurance> implements InsuranceDao {

    @Autowired
    protected InsuranceDaoImpl(SessionFactory sessionFactory) {
        super(Insurance.class, sessionFactory);
    }
}
