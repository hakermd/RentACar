package com.rentacar.dao;

import com.rentacar.model.Rent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aplesca on 6/13/2017.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class RentDaoImpl extends AbstractHibernateDAO<Rent> implements RentDao {
    protected RentDaoImpl() {
        super(Rent.class);
    }
}