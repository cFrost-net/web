package net.cfrost.web.core.security.authentication.service;

import java.util.List;

import net.cfrost.web.core.base.service.IBaseService;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;
import net.cfrost.web.core.security.authentication.entity.User;

public interface IAuthService extends IBaseService {

    public List<RoleAuth> findAllRoleAuth();
    
    public User findUserByName(String username, boolean loadRoles);

    public Long createUser(String username, String password);

    public List<User> findAllUsers(boolean loadRoles);
}
