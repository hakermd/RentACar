package com.rentacar.dao;

import com.rentacar.model.Booking;
import com.rentacar.model.Car;
import com.rentacar.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Autowired
    private SessionFactory sessionFactory;

    protected BookingDaoImpl() {
        super(Booking.class);
    }

    @Override
    public Booking findBookingByCode(String bookingCode) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        criteria.select(bookingRoot).where(builder.equal(bookingRoot.get("bookingCode"), bookingCode));
        TypedQuery query = session.createQuery(criteria);
        return (Booking) query.getSingleResult();
    }

    @Override
    public Booking getBookingByCar(Car car) {
        Session session = sessionFactory.getCurrentSession();
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
        return (Booking) query.getSingleResult();
    }

    @Override
    public Booking getBookingByPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
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
        return (Booking) query.getSingleResult();
    }
}
