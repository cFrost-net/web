package net.cfrost.web.core.dao.hibernate5;

import java.io.Serializable;
import java.util.List;

import net.cfrost.web.core.domain.BaseEntity;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseDao<T extends BaseEntity<?>>{

    T get(Serializable id);
    
    Serializable save(T entity);
    
    void update(T entity);
    
    Serializable saveOrUpdate(T entity);
    
    void delete(T entity);
    
    void delete(Serializable id);
    
    List<T> findAll();
    
    long findCount();
    
    List<T> findBy(DetachedCriteria detachedCriteria);
    
    List<T> findPageBy(DetachedCriteria detachedCriteria, int pageIndex, int pageSize);
}
