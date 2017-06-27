package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.Person;
import com.rentacar.model.Rent;
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
public class RentDaoImpl extends AbstractHibernateDAO<Rent> implements RentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CarDao carDao;

    protected RentDaoImpl() {
        super(Rent.class);
    }

    @Override
    public Rent getRentByCar(Car car) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Rent> rentRoot = criteria.from(Rent.class);
        Predicate carRestriction = builder.and(
                builder.equal(rentRoot.get("car"), car.getCarId()),
                builder.equal(rentRoot.get("active"), true)
        );
        criteria.select(rentRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        return (Rent) query.getSingleResult();
    }

    @Override
    public Rent getRentByPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Rent> rentRoot = criteria.from(Rent.class);
        Predicate carRestriction = builder.and(
                builder.equal(rentRoot.get("personId"), person.getPersonId()),
                builder.equal(rentRoot.get("rentActive"), true)
        );
        criteria.select(rentRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        return (Rent) query.getSingleResult();
    }
}
