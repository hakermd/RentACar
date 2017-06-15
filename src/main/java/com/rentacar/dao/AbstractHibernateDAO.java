package com.rentacar.dao;

import com.rentacar.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aplesca on 5/16/2017.
 */
public abstract class AbstractHibernateDAO<T extends Serializable> implements DAO<T> {
    private Class<T> clazz;

    protected AbstractHibernateDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    private Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//    Session session;

    @Override
    public T findOne(final long id) {
         session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        T t = (T) session.get(clazz, id);
        tx.commit();
        return t;
    }

    @Override
    public List<T> findAll() {
         session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<T> list = session.createQuery("from " + clazz.getName()).getResultList();
        tx.commit();
        return list;
    }

    @Override
//    @Transactional
    public void save(final T entity) {
         session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
            tx.commit();
//        getCurrentSession().close();
    }

    @Override
//    @Transactional
    public void update(final T entity) {
         session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(entity);
            tx.commit();
//        getCurrentSession().close();
    }

    @Override
    public void delete(final T entity) {
         session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.delete(entity);
            tx.commit();
//        getCurrentSession().close();
    }

    @Override
    public void deleteById(final long id) {
        final T entity = findOne(id);
        delete(entity);
    }
//
//    protected final Session getCurrentSession() {
//        try {
//            session = HibernateUtil.getSessionFactory().getCurrentSession();
//        } catch (HibernateException e) {
//            session = HibernateUtil.getSessionFactory().openSession();
//        }
//        return session;
//    }
}
