package com.rentacar.dao;

import com.rentacar.model.Car;
import com.rentacar.model.CarFilter;
import com.rentacar.model.enums.CarAvailability;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Andrei.Plesca
 */
@Repository
public class CarDaoImpl extends AbstractHibernateDAO<Car> implements CarDao {

    @Autowired
    private SessionFactory sessionFactory;

    public CarDaoImpl() {
        super(Car.class);
    }

    public List<Car> searchACarByStatus(CarAvailability carAvailability) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Car c where c.availability  =  :carAvailability";

        List<Car> carList = session.createQuery(hql)
                .setParameter("carAvailability", carAvailability)
                .list();

        return carList;
    }

    public List<Car> searchACarByCriteria(CarFilter filter) {
        // Create CriteriaBuilder
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Car> carRoot = criteria.from(Car.class);
        Predicate carRestriction = builder.and(
                filter.getEconomyClass() != null ? builder.equal(carRoot.get("economyClass"), filter.getEconomyClass()) : builder.conjunction(),
//                car.getManufacturer() != null ? builder.equal(carRoot.get("manufacturer"), car.getManufacturer()) : builder.conjunction(),
                filter.getCarType() != null ? builder.equal(carRoot.get("type"), filter.getCarType()) : builder.conjunction(),
                filter.getOptions() != null ? builder.equal(carRoot.get("options"), filter.getOptions()) : builder.conjunction(),
                filter.getTransmission() != null ? builder.equal(carRoot.get("transmission"), filter.getTransmission()) : builder.conjunction(),
                builder.equal(carRoot.get("availability"), CarAvailability.AVAILABLE),
                filter.getYearOfProduction() > 0 ? builder.equal(carRoot.get("yearOfProduction"), filter.getYearOfProduction()) : builder.conjunction()
        );
        criteria.select(carRoot).where(builder.and(carRestriction));
        TypedQuery query = session.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public Car findCarByWinCode(String carWinCode) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Create CriteriaQuery
        CriteriaQuery criteria = builder.createQuery();
        Root<Car> carRoot = criteria.from(Car.class);
        criteria.select(carRoot).where(builder.equal(carRoot.get("winCode"), carWinCode));
        TypedQuery query = session.createQuery(criteria);
        return (Car) query.getSingleResult();
    }

}
