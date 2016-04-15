package net.cfrost.web.core.base.dao.hibernate5.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import net.cfrost.common.Generic;
import net.cfrost.web.core.base.dao.hibernate5.IBaseDao;
import net.cfrost.web.core.base.entity.BaseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

public abstract class BaseDao<T extends BaseEntity<?>> implements IBaseDao<T> {
    
    protected final Logger log = LogManager.getLogger();

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDao(){
        this.entityClass = (Class<T>) Generic.findSuperClassGenricType(this.getClass(), 0);
    }

    @Override
    public T get(Serializable id) {
        return this.getSessionFactory().getCurrentSession().get(this.entityClass, id);
    }

    @Override
    public Serializable save(T entity) {
        return this.getSessionFactory().getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        this.getSessionFactory().getCurrentSession().update(entity);

    }

    @Override
    public Serializable saveOrUpdate(T entity) {
        
        if(entity.getId() != null){
            this.update(entity);
            return entity.getId();
        } else {
            return this.save(entity);
        }
    }

    @Override
    public void delete(T entity) {
        this.getSessionFactory().getCurrentSession().delete(entity);
    }

    @Override
    public void delete(Serializable id) {
        this.getSessionFactory().getCurrentSession()
            .createQuery("delete "+ this.entityClass.getSimpleName() + " en where en.id = ?0")
            .setParameter(0, id)
            .executeUpdate();
    }

    @Override
    public List<T> findAll() {
        return this.find("select en from "+ this.entityClass.getSimpleName() + " en");
    }

    @Override
    public long findCount() {
        List<?> l = this.find("select count(*) from "+ this.entityClass.getSimpleName());

        if(l!=null && l.size() == 1)
            return (Long)l.get(0);
        return 0;
    }


    @SuppressWarnings("unchecked")
    protected List<T> find(String hql){
        return this.getSessionFactory().getCurrentSession().createQuery(hql).list();
    }


    @SuppressWarnings("unchecked")
    protected List<T> find(String hql, Object... params){
        Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);

        for(int i = 0; i < params.length; i++){
            query.setParameter(i, params[i]);
        }

        return query.list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> findBy(DetachedCriteria detachedCriteria) {
        return detachedCriteria.getExecutableCriteria(this.getSessionFactory().getCurrentSession()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findPageBy(DetachedCriteria detachedCriteria, int pageIndex, int pageSize) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSessionFactory().getCurrentSession());
        criteria.setFirstResult(pageIndex);
        criteria.setMaxResults(pageSize);
        return criteria.list();
    }
}
