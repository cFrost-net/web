package net.cfrost.web.core.security.authentication.service;

import java.util.List;

import net.cfrost.web.core.base.service.IBaseService;
import net.cfrost.web.core.security.authentication.entity.User;

public interface IUserService extends IBaseService {
    
    public User findUserByName(String username, boolean loadRoles);

    public void createUser(String username, String password);

    public List<User> findAllUsers(boolean loadRoles);
}
