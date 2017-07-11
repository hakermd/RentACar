package com.rentacar.dao;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Andrei.Plesca
 */
@Repository
public class BookingDaoImpl extends AbstractHibernateDAO<Booking> implements BookingDao {

    private static final Logger logger = LoggerFactory
            .getLogger(BookingDaoImpl.class);

    private final SessionFactory sessionFactory;

    @Autowired
    protected BookingDaoImpl(SessionFactory sessionFactory) {
        super(Booking.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Booking findBookingByCode(String bookingCode) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        criteria.select(bookingRoot).where(builder.equal(bookingRoot.get("bookingCode"), bookingCode));
        TypedQuery query = session.createQuery(criteria);
        try {
            return (Booking) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Booking Not Found! ");
            return null;
        }
    }

    @Override
    public Booking getBookingByCar(Car car) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        Predicate carRestriction = builder.and(
                builder.equal(bookingRoot.get("car"), car.getCarId()),
                builder.equal(bookingRoot.get("active"), true)
        );
        criteria.select(bookingRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        try {
            return (Booking) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Booking Not Found! ");
            return null;
        }
    }

    @Override
    public Booking getBookingByPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        Predicate carRestriction = builder.and(
                builder.equal(bookingRoot.get("person"), person.getPersonId()),
                builder.equal(bookingRoot.get("active"), true)
        );
        criteria.select(bookingRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        try {
            return (Booking) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Booking Not Found! ");
            return null;
        }
    }
}
