package net.cfrost.web.core.security.authentication.dao.impl;

import java.util.List;

import net.cfrost.web.core.base.dao.hibernate5.impl.BaseDao;
import net.cfrost.web.core.security.authentication.dao.IAuthorityDao;
import net.cfrost.web.core.security.authentication.entity.Authority;

public class AuthorityDao extends BaseDao<Authority> implements IAuthorityDao {

    @Override
    public List<Authority> findAllRoles() {
        return this.findAll();
    }

}
