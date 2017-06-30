package com.rentacar.dao;

import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public interface DAO<T extends Serializable> {
    T findOne(long id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(long id);

    public SessionFactory getSessionFactory();
}
