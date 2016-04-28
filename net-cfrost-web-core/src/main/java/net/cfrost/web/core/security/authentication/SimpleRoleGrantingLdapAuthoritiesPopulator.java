package net.cfrost.web.core.security.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.cfrost.web.core.security.authentication.entity.Role;
import net.cfrost.web.core.security.authentication.service.IUserService;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class SimpleRoleGrantingLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {
    
    private IUserService userService;

    public void setAuthService(IUserService userService) {
        this.userService = userService;
    }

    public Collection<SimpleGrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {

        System.out.println("赋权限");
        net.cfrost.web.core.security.authentication.entity.User user = this.userService
                .findUserByName(username, true);

        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        if (user == null){
            System.out.println("无用户");            
            return null;
        }

        if (user.getRoles() != null || (!user.getRoles().isEmpty())) {
            for (Role role : user.getRoles()) {
                if (!role.getName().equals(Role.ANONYMOUS))
                    list.add(new SimpleGrantedAuthority(role.getName()));
                System.out.println(role);   
            }
        }
        
        return list;
    }
}
