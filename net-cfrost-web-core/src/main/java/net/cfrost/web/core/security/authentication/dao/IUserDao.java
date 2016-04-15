package net.cfrost.web.core.security.authentication.dao;

import net.cfrost.web.core.base.dao.hibernate5.IBaseDao;
import net.cfrost.web.core.security.authentication.entity.User;

public interface IUserDao extends IBaseDao<User> {

    User findUserByName(String username);

}
