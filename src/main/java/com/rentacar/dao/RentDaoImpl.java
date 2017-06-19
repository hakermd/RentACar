package com.rentacar.dao;

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
public class RentDaoImpl extends AbstractHibernateDAO<Rent> implements RentDao {
    protected RentDaoImpl() {
        super(Rent.class);
    }

    @Override
    public Rent getRentByCar(Car car) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Rent> rentRoot = criteria.from(Rent.class);
        Predicate carRestriction = builder.and(
                builder.equal(rentRoot.get("carId"), car.getCarId()),
                builder.equal(rentRoot.get("rentActive"), true)
        );
        criteria.select(rentRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        Rent rent = (Rent) query.getSingleResult();
        tx.commit();
        return rent;
    }

    @Override
    public Rent getRentByPerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
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
        Rent rent = (Rent) query.getSingleResult();
        tx.commit();
        return rent;
    }
}
