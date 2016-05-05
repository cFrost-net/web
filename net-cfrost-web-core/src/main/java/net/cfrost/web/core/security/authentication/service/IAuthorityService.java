package net.cfrost.web.core.security.authentication.service;

import java.util.List;

import net.cfrost.web.core.base.service.IBaseService;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;

public interface IAuthorityService extends IBaseService {

    public List<RoleAuth> findAllRoleAuth();
}
