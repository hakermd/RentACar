package com.rentacar.dao;

import com.rentacar.model.Person;
import com.rentacar.model.enums.UserRole;
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
public class PersonDaoImpl extends AbstractHibernateDAO<Person> implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory
            .getLogger(PersonDaoImpl.class);

    protected PersonDaoImpl() {
        super(Person.class);
    }

    @Override
    public Person logIn(String email, String password) {
        try {
            return login(email, password, UserRole.USER);
        } catch (NoResultException e) {
            logger.info("Registration Not Found! ");
            return null;
        }
    }

    @Override
    public Person adminLogIn(String email, String password) {
        try {
            return login(email, password, UserRole.ADMIN);
        } catch (NoResultException e) {
            logger.info("Registration Not Found! ");
            return null;
        }
    }

    @Override
    public Person findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery criteria = builder.createQuery();
            Root<Person> personRoot = criteria.from(Person.class);
            criteria.select(personRoot).where(builder.equal(personRoot.get("email"), email));
            TypedQuery query = session.createQuery(criteria);
            Person person = (Person) query.getSingleResult();
            return person;
        } catch (NoResultException e) {
            logger.info("Registration Not Found! ");
            return null;
        }
    }

    private Person login(String email, String password, UserRole userRole) {
        Session session = sessionFactory.getCurrentSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery criteria = builder.createQuery();
            Root<Person> personRoot = criteria.from(Person.class);
            Predicate carRestriction = builder.and(
                    builder.equal(personRoot.get("email"), email),
                    builder.equal(personRoot.get("password"), password),
                    builder.equal(personRoot.get("userRole"), userRole)
            );
            criteria.select(personRoot).where(builder.and(carRestriction));
            TypedQuery query = session.createQuery(criteria);
            Person person = (Person) query.getSingleResult();
            return person;
        } catch (NoResultException e) {
            logger.info("Registration Not Found! ");
            return null;
        }
    }
}
