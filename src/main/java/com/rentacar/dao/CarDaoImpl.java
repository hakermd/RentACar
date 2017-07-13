package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Repository
public class CarDaoImpl extends AbstractHibernateDAO<Car> implements CarDao {
    private static final Logger logger = LoggerFactory
            .getLogger(Car.class);

    private static final String CAR_NOT_FOUND = "Car Not Found! ";
    private final SessionFactory sessionFactory;

    @Autowired
    public CarDaoImpl(SessionFactory sessionFactory) {
        super(Car.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Car> searchACarByStatus(CarAvailability carAvailability) {
        // Create CriteriaBuilder
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Car> carRoot = criteria.from(Car.class);
        criteria.select(carRoot).where(builder.and(builder.equal(carRoot.get("availability"), carAvailability)));
        TypedQuery query = session.createQuery(criteria);

        try {
            return (List<Car>) query.getResultList();
        } catch (NoResultException e) {
            logger.info(CAR_NOT_FOUND);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Car> searchACarByCriteria(CarFilter filter) {
        // Create CriteriaBuilder
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Car> carRoot = criteria.from(Car.class);
        Predicate carRestriction = builder.and(
                filter.getEconomyClass() != null ? builder.equal(carRoot.get("economyClass"), filter.getEconomyClass()) : builder.conjunction(),
                filter.getCarType() != null ? builder.equal(carRoot.get("type"), filter.getCarType()) : builder.conjunction(),
                filter.getOptions() != null ? builder.equal(carRoot.get("options"), filter.getOptions()) : builder.conjunction(),
                filter.getTransmission() != null ? builder.equal(carRoot.get("transmission"), filter.getTransmission()) : builder.conjunction(),
                filter.getCarAvailability() != null ? builder.equal(carRoot.get("availability"), filter.getCarAvailability()) : builder.conjunction(),
                (filter.getYearOfProduction() != null && !filter.getYearOfProduction().isEmpty() && !filter.getYearOfProduction().equals("0")) ? builder.equal(carRoot.get("yearOfProduction"), filter.getYearOfProduction()) : builder.conjunction());
        criteria.select(carRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);

        try {
            return (List<Car>) query.getResultList();
        } catch (NoResultException e) {
            logger.info(CAR_NOT_FOUND);
            return new ArrayList<>();
        }
    }

    @Override
    public Car findCarByWinCode(String carWinCode) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Car> carRoot = criteria.from(Car.class);
        criteria.select(carRoot).where(builder.equal(carRoot.get("winCode"), carWinCode));
        TypedQuery query = session.createQuery(criteria);

        try {
            return (Car) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(CAR_NOT_FOUND);
            return null;
        }
    }

    @Override
    public Car findCarByRegistrationNumber(String registrationNumber) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery<Object> criteria = builder.createQuery();
        Root<Car> carRoot = criteria.from(Car.class);
        criteria.select(carRoot).where(builder.equal(carRoot.get("registrationNumber"), registrationNumber));
        TypedQuery query = session.createQuery(criteria);

        try {
            return (Car) query.getSingleResult();
        } catch (NoResultException e) {
            logger.info(CAR_NOT_FOUND);
            return null;
        }
    }

}
