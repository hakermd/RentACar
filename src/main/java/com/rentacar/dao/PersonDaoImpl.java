package com.rentacar.dao;

import com.rentacar.model.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrei.Plesca
 */
@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class PersonDaoImpl extends AbstractHibernateDAO<Person> implements PersonDao {
    protected PersonDaoImpl() {
        super(Person.class);
    }
}
