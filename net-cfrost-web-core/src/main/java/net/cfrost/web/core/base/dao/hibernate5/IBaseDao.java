package net.cfrost.web.core.base.dao.hibernate5;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.cfrost.web.core.base.entity.BaseEntity;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseDao<T extends BaseEntity<?>>{

    T load(Serializable id);
    
    Serializable save(T entity);
    
    void update(T entity);
    
    void saveOrUpdate(T entity);
    
    void saveOrUpdate(Collection<T> entities);
    
    void delete(T entity);
    
    void delete(Serializable id);
    
    List<T> findAll();
    
    List<T> findBy(DetachedCriteria detachedCriteria);
    
    List<T> findPageBy(DetachedCriteria detachedCriteria, int pageIndex, int pageSize);
    
    List<T> findBy(String hql);
    
    List<T> findBy(String hql, Object... params);
    
    long findCount();
}
