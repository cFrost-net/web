package net.cfrost.web.core.security.authentication;

import net.cfrost.web.core.security.authentication.service.IAuthService;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class MyLdapAuthenticationProvider extends LdapAuthenticationProvider {

    private IAuthService authService;

    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }
    
    public MyLdapAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator) {
        super(authenticator, authoritiesPopulator);
    }

    @Override
    protected DirContextOperations doAuthentication(UsernamePasswordAuthenticationToken authentication){
        if(this.authService.findUserByName(authentication.getName(), false)==null){
            throw new MyAuthenticationException("无用户");
        }
        return super.doAuthentication(authentication);
    }
}
