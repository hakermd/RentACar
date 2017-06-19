package com.rentacar.dao;

import com.rentacar.controller.PersonController;
import com.rentacar.model.Person;
import com.rentacar.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class PersonDaoImpl extends AbstractHibernateDAO<Person> implements PersonDao {
    private static final Logger logger = LoggerFactory
            .getLogger(PersonController.class);

    protected PersonDaoImpl() {
        super(Person.class);
    }

    @Override
    public Person logIn(String email, String password) {
        // Create CriteriaBuilder
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {

            Transaction tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // Create CriteriaQuery
            CriteriaQuery criteria = builder.createQuery();
            Root<Person> personRoot = criteria.from(Person.class);
            Predicate carRestriction = builder.and(
                    builder.equal(personRoot.get("email"), email),
                    builder.equal(personRoot.get("password"), password)
            );
            criteria.select(personRoot).where(builder.and(carRestriction));
            TypedQuery query = session.createQuery(criteria);
            Person person = (Person) query.getSingleResult();
            tx.commit();
            return person;
        } catch (NoResultException e) {
            logger.info("Registration Not Found! ");
            return null;
        }
    }
}
