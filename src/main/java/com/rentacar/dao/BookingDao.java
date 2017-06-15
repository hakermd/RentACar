package com.rentacar.dao;

import com.rentacar.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aplesca on 5/17/2017.
 */

public interface BookingDao extends DAO<Booking>{
}
