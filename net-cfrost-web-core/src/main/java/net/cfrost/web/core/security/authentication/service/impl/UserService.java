package net.cfrost.web.core.security.authentication.service.impl;

import java.util.List;

import net.cfrost.web.core.base.service.impl.BaseService;
import net.cfrost.web.core.security.authentication.dao.IUserDao;
import net.cfrost.web.core.security.authentication.entity.User;
import net.cfrost.web.core.security.authentication.service.IUserService;

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
    public Long createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        return (Long)this.userDao.save(user);
        
    }

    @Override
    public User findUserByName(String username, boolean loadRoles) {        
        User user = this.userDao.findUserByName(username);
        if(user == null) return null;
        if(loadRoles) {
            user.getAuthorities().size();
        }
        return user;
    }

    @Override
    public List<User> findAllUsers(boolean loadRoles) {
        List<User> result = this.userDao.findAll();
        if(loadRoles && result != null){
            for(User user : result){
                user.getAuthorities().size();
            }
        }
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findUserByName(username);
        if(user == null)
            throw new UsernameNotFoundException("User \"" + username + "\" not found.");
        
        user.getAuthorities().size();
        
        return user;
    }
    
    
}
