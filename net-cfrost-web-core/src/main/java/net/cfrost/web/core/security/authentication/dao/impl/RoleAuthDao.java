package net.cfrost.web.core.security.authentication.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import net.cfrost.web.core.base.dao.hibernate5.impl.BaseDao;
import net.cfrost.web.core.security.authentication.dao.IRoleAuthDao;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;

public class RoleAuthDao extends BaseDao<RoleAuth> implements IRoleAuthDao {

    @Override
    public List<RoleAuth> findAppRoleAuth() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(super.entityClass);
        detachedCriteria.addOrder(Order.asc("order"));
        return this.findBy(detachedCriteria);
    }
}
