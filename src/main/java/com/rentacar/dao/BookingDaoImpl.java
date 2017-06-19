package com.rentacar.dao;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
import com.rentacar.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Andrei.Plesca
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class BookingDaoImpl extends AbstractHibernateDAO<Booking> implements BookingDao {
    protected BookingDaoImpl() {
        super(Booking.class);
    }

    @Override
    public Booking findBookingByCode(String bookingCode) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        criteria.select(bookingRoot).where(builder.equal(bookingRoot.get("bookingCode"), bookingCode));
        TypedQuery query = session.createQuery(criteria);
        Booking booking = (Booking) query.getSingleResult();
        tx.commit();
        return booking;
    }

    @Override
    public Booking getBookingByCar(Car car) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        Predicate carRestriction = builder.and(
                builder.equal(bookingRoot.get("carId"), car.getCarId()),
                builder.equal(bookingRoot.get("bookingActive"), true)
        );
        criteria.select(bookingRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        Booking booking = (Booking) query.getSingleResult();
        tx.commit();
        return booking;
    }

    @Override
    public Booking getBookingByPerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        Predicate carRestriction = builder.and(
                builder.equal(bookingRoot.get("personId"), person.getPersonId()),
                builder.equal(bookingRoot.get("bookingActive"), true)
        );
        criteria.select(bookingRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        Booking booking = (Booking) query.getSingleResult();
        tx.commit();
        return booking;
    }
}
