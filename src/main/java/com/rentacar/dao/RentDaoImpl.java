package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
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
public class RentDaoImpl extends AbstractHibernateDAO<Rent> implements RentDao {
    private static final Logger logger = LoggerFactory
            .getLogger(RentDaoImpl.class);

    private static final String RENT_NOT_FOUND = "Rent Not Found! ";
    private final SessionFactory sessionFactory;

    @Autowired
    protected RentDaoImpl(SessionFactory sessionFactory) {
        super(Rent.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Rent getRentByCar(Car car) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Rent> rentRoot = criteria.from(Rent.class);
        Predicate carRestriction = builder.and(
                builder.equal(rentRoot.get("car"), car.getCarId()),
                builder.equal(rentRoot.get("active"), true)
        );
        criteria.select(rentRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);

        try {
            return (Rent) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(RENT_NOT_FOUND);
            return null;
        }
    }

    @Override
    public Rent getRentByPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Rent> rentRoot = criteria.from(Rent.class);
        Predicate carRestriction = builder.and(
                builder.equal(rentRoot.get("person"), person.getPersonId()),
                builder.equal(rentRoot.get("active"), true)
        );
        criteria.select(rentRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        try {
            return (Rent) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(RENT_NOT_FOUND);
            return null;
        }
    }
}
