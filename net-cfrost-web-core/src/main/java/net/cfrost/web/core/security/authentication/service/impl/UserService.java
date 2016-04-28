package net.cfrost.web.core.security.authentication.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.cfrost.web.core.base.service.impl.BaseService;
import net.cfrost.web.core.security.authentication.dao.IUserDao;
import net.cfrost.web.core.security.authentication.entity.Role;
import net.cfrost.web.core.security.authentication.entity.User;
import net.cfrost.web.core.security.authentication.service.IUserService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserService extends BaseService implements IUserService, UserDetailsService {

    private IUserDao userDao;
    
    private PasswordEncoder passwordEncoder;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
        
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
