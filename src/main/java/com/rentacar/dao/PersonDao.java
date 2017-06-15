package com.rentacar.dao;

import com.rentacar.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aplesca on 5/8/2017.
 */
public interface PersonDao extends DAO<Person> {
}
