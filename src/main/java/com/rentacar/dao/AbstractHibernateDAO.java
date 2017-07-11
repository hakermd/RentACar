package com.rentacar.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrei.Plesca
 */
public abstract class AbstractHibernateDAO<T extends Serializable> implements DAO<T> {

    private final SessionFactory sessionFactory;
    private Class<T> clazz;

    @Autowired
    protected AbstractHibernateDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T findOne(final long id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public void save(final T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(final T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(final T entity) {
        if (entity != null)
            sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(final long id) {
        final T entity = findOne(id);
        delete(entity);
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
