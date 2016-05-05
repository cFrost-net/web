package net.cfrost.web.core.security.authentication.service.impl;

import java.util.List;

import net.cfrost.web.core.base.service.impl.BaseService;
import net.cfrost.web.core.security.authentication.dao.IRoleAuthDao;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;
import net.cfrost.web.core.security.authentication.service.IAuthorityService;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AuthorityService extends BaseService implements IAuthorityService {
    
    private IRoleAuthDao roleAuthDao;

    public void setRoleAuthDao(IRoleAuthDao roleAuthDao) {
        this.roleAuthDao = roleAuthDao;
    }

    @Override
    public List<RoleAuth> findAllRoleAuth() {
        List<RoleAuth> roleAuthList =  this.roleAuthDao.findAppRoleAuth();        
        if(roleAuthList == null|| roleAuthList.isEmpty()) return null;
        
        for(RoleAuth roleAuth : roleAuthList){
            roleAuth.getRoles().size();
        }
        
        return roleAuthList;
    }    
}
