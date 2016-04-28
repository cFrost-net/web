package net.cfrost.web.core.security.authentication.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.cfrost.common.StringTool;
import net.cfrost.web.core.base.service.impl.BaseService;
import net.cfrost.web.core.security.authentication.dao.IRoleAuthDao;
import net.cfrost.web.core.security.authentication.dao.IRoleDao;
import net.cfrost.web.core.security.authentication.dao.IUserDao;
import net.cfrost.web.core.security.authentication.entity.Role;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;
import net.cfrost.web.core.security.authentication.entity.User;
import net.cfrost.web.core.security.authentication.service.IAuthService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AuthService extends BaseService implements IAuthService, UserDetailsService {

    private IUserDao userDao;
    private IRoleDao roleDao;
    private IRoleAuthDao roleAuthDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setRoleAuthDao(IRoleAuthDao roleAuthDao) {
        this.roleAuthDao = roleAuthDao;
    }
    
    public AuthService(){;
    }

    @Override
    public User findUserByName(String username, boolean loadRoles) {        
        User user = this.userDao.findUserByName(username);
        if(user == null) return null;
        if(loadRoles) {
            Set<Role> roles = user.getRoles();
            if(roles != null){
                for(Role role : roles){
                    role.getName();
                }
            }
        }
        return user;
    }

    @Override
    public List<RoleAuth> findAllRoleAuth() {
        List<RoleAuth> roleAuthList =  this.roleAuthDao.findAppRoleAuth();        
        if(roleAuthList == null|| roleAuthList.isEmpty()) return null;

        List<Role> roleList  = this.roleDao.findAllRoles();
        if(roleList == null|| roleList.isEmpty()) return roleAuthList;
        
        for(RoleAuth  roleAuth : roleAuthList) {
            Set<String> roleIdSet = new HashSet<>(Arrays.asList(roleAuth.getRoleIds().trim().replaceAll(" ", "").split(",")));
            for(Role role : roleList){
                if(StringTool.isNull(role.getName()))
                    continue;
                
                if(roleIdSet.contains(role.getId().toString())){
                    if(roleAuth.getRoles() == null){
                        roleAuth.setRoles(new HashSet<String>());
                    }
                    roleAuth.getRoles().add(role.getName().trim());
                }
            }
        }
        return roleAuthList;
    }

    @Override
    public Long createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setEnabled(true);
        return (Long)this.userDao.save(user);
        
    }

    @Override
    public List<User> findAllUsers(boolean loadRoles) {
        List<User> result = this.userDao.findAll();
        if(loadRoles && result != null){
            for(User user : result){
                Set<Role> roles = user.getRoles();
                if(roles != null){
                    for(Role role : roles){
                        role.getName();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findUserByName(username);
        if(user == null)
            throw new UsernameNotFoundException("User \"" + username + "\" not found.");
        
        Set<Role> roles = user.getRoles();
        HashSet<GrantedAuthority> authoritySet = new HashSet<>();
        if(roles != null){
            for(Role role : roles){
              if (!role.getName().equals(Role.ANONYMOUS))
                  authoritySet.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        user.setAuthorities(authoritySet);
        
        return user;
    }
    
    
}
