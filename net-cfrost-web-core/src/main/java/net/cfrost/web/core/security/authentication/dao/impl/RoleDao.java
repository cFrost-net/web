package net.cfrost.web.core.security.authentication.dao.impl;

import java.util.List;

import net.cfrost.web.core.base.dao.hibernate5.impl.BaseDao;
import net.cfrost.web.core.security.authentication.dao.IRoleDao;
import net.cfrost.web.core.security.authentication.entity.Role;

public class RoleDao extends BaseDao<Role> implements IRoleDao {

    @Override
    public List<Role> findAllRoles() {
        return this.findAll();
    }

}
