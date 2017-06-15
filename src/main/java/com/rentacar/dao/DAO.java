package com.rentacar.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aplesca on 5/17/2017.
 */
public interface DAO<T extends Serializable> {
    T findOne(long id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(long id);
}
