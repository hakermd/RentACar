package com.rentacar.dao;

import com.rentacar.model.Booking;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrei.Plesca
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class BookingDaoImpl extends AbstractHibernateDAO<Booking> implements BookingDao {
    protected BookingDaoImpl() {
        super(Booking.class);
    }
}
