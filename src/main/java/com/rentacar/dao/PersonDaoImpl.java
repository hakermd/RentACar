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

import static com.rentacar.util.PersonModelConstants.*;

/**
 * Created by Andrei.Plesca
 */
@Repository
public class PersonDaoImpl extends AbstractHibernateDAO<Person> implements PersonDao {

    private static final Logger logger = LoggerFactory
            .getLogger(PersonDaoImpl.class);

    private static final String REGISTRATION_NOT_FOUND = "Registration Not Found! ";
    private final SessionFactory sessionFactory;

    @Autowired
    protected PersonDaoImpl(SessionFactory sessionFactory) {
        super(Person.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Person logIn(String email, String password) {
        try {
            return doLogin(email, password, UserRole.USER);
        } catch (NoResultException e) {
            logger.info(REGISTRATION_NOT_FOUND);
            return null;
        }
    }

    @Override
    public Person adminLogIn(String email, String password) {
        try {
            return doLogin(email, password, UserRole.ADMIN);
        } catch (NoResultException e) {
            logger.info(REGISTRATION_NOT_FOUND);
            return null;
        }
    }

    @Override
    public Person findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Person> personRoot = criteria.from(Person.class);
        criteria.select(personRoot).where(builder.equal(personRoot.get("email"), email));
        TypedQuery query = session.createQuery(criteria);
        try {
            return (Person) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(REGISTRATION_NOT_FOUND);
            return null;
        }
    }

    private Person doLogin(String email, String password, UserRole userRole) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Person> personRoot = criteria.from(Person.class);
        Predicate carRestriction = builder.and(
                builder.equal(personRoot.get(PERSON_EMAIL), email),
                builder.equal(personRoot.get(PERSON_USER_PSW), password),
                builder.equal(personRoot.get(PERSON_ROLE), userRole)
        );
        criteria.select(personRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        try {
            return (Person) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(REGISTRATION_NOT_FOUND);
            return null;
        }
    }
}
