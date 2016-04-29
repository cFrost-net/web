package net.cfrost.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.Filter;

import net.cfrost.web.core.security.authentication.entity.Role;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;
import net.cfrost.web.core.security.authentication.provider.UsernamePasswordAuthenticationProvider;
import net.cfrost.web.core.security.authentication.service.IAuthorityService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.authentication.UserDetailsServiceLdapAuthoritiesPopulator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private final static String SPRING_SECURITY_LOGOUT_URL="/logout";

    protected final Logger log = LogManager.getLogger();
    
    @Resource
    private IAuthorityService authorityService;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Value("${springSecurity.ignoreUrls}")
    private String ignoreUrls;
    
    @Override
    public void configure(WebSecurity security){
        String[] antMatchers = this.ignoreUrls.trim().split(",");
        security.ignoring().antMatchers(antMatchers);
        this.log.info("SPRING SECURITY CONFIG: Add ignore urls: \""+ Arrays.asList(antMatchers) +"\"");
    }
    
    @Override
    public void configure(HttpSecurity security) throws Exception{
        
        List<RoleAuth> roleAuthList = this.authorityService.findAllRoleAuth();
        
        if(roleAuthList != null){
            setAuth:for(RoleAuth roleAuth : roleAuthList){
                if(roleAuth.getRoles() == null || roleAuth.getRoles().isEmpty()) continue;

                Set<Role> roleSet = roleAuth.getRoles();
                Set<String> roleNameList = new HashSet<>();
                
                for(Role role : roleSet){
                    if(Role.ANONYMOUS.equals(role.getName())){
                        security.authorizeRequests().antMatchers(roleAuth.getUrlMatcher()).permitAll();
                        this.log.info("SPRING SECURITY CONFIG: Add role to "+roleAuth.getUrlMatcher()+" PERMIT_ALL");
                        continue setAuth;
                    }                    
                    roleNameList.add(role.getName());
                }
                
                String[] roles = roleNameList.toArray(new String[0]);
                security.authorizeRequests().antMatchers(roleAuth.getUrlMatcher()).hasAnyAuthority(roles);
                this.log.info("SPRING SECURITY CONFIG: Add roles to "+roleAuth.getUrlMatcher()+" : "+roleNameList);
            }
        }
        
        security.authorizeRequests().anyRequest().denyAll();
        
        security.sessionManagement()
            .sessionFixation().migrateSession()
            .maximumSessions(1).maxSessionsPreventsLogin(true)
            .and().and().csrf().disable();
        
        security.logout()
            .logoutUrl(SecurityConfiguration.SPRING_SECURITY_LOGOUT_URL).logoutSuccessUrl("/login?loggedOut")
            .invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .permitAll();
        
        if(enableCas){
            security.addFilter(this.casAuthenticationFilter())
                .addFilterBefore(this.singleLogoutFilter(), CasAuthenticationFilter.class)
                .addFilterBefore(this.requestSingleLogoutFilter(), LogoutFilter.class)
                .exceptionHandling().authenticationEntryPoint(this.casEntryPoint());
        } else {
            security.formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if(this.enableCas){
            this.log.info("SPRING SECURITY CONFIG: USE CAS"); 
            return;
        }
        if(this.enableLdap){
            auth.authenticationProvider(this.ldapAuthenticationProvider());
            this.log.info("SPRING SECURITY CONFIG: USE LDAP");
            return;
        }
        if(this.enableLocalUserDatebase) {
            auth.authenticationProvider(this.usernamePasswordAuthenticationProvider());
            this.log.info("SPRING SECURITY CONFIG: USE LOCAL USER DATABASE");
            return;
        }
        throw new Exception("必须指定一种用户验证方式");
    }
    
    //=======LOCAL_USER_DATABASE======
    @Value("${localUserDatebase.enabled}")
    private boolean enableLocalUserDatebase;
    private AuthenticationProvider usernamePasswordAuthenticationProvider;
    public AuthenticationProvider usernamePasswordAuthenticationProvider(){
        if(this.usernamePasswordAuthenticationProvider != null) return this.usernamePasswordAuthenticationProvider;
        
        UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider = new UsernamePasswordAuthenticationProvider();
        usernamePasswordAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
        usernamePasswordAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        
        return usernamePasswordAuthenticationProvider;
    }
    
    //=======LDAP======
    @Value("${ldap.enabled}")
    private boolean enableLdap;
    @Value("${ldap.url}")
    private String ldapUrl;
    @Value("${ldap.userDN}")
    private String ldapUserDN;
    @Value("${ldap.password}")
    private String ldapPassword;
    @Value("${ldap.searchBase}")
    private String ldapSearchBase;
    @Value("${ldap.searchFilter}")
    private String ldapSearchFilter;
    
    private AuthenticationProvider ldapAuthenticationProvider;
    private LdapAuthoritiesPopulator ldapAuthoritiesPopulator;
    private LdapAuthenticator ldapAuthenticator;
    private LdapUserSearch userSearch;
    private BaseLdapPathContextSource ldapContextSource;
    
    private AuthenticationProvider ldapAuthenticationProvider(){
        if(this.ldapAuthenticationProvider != null) return this.ldapAuthenticationProvider;
        
        return new LdapAuthenticationProvider(this.ldapAuthenticator(), this.ldapAuthoritiesPopulator());
    }

    private LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
        if(this.ldapAuthoritiesPopulator != null) return this.ldapAuthoritiesPopulator;
        
        return new UserDetailsServiceLdapAuthoritiesPopulator(this.userDetailsService);
    }

    private LdapAuthenticator ldapAuthenticator() {
        if(this.ldapAuthenticator != null) return this.ldapAuthenticator;
        
        BindAuthenticator BindAuthenticator = new BindAuthenticator(this.ldapContextSource());
        BindAuthenticator.setUserSearch(this.userSearch());
        return BindAuthenticator;
    }

    @Bean
    public BaseLdapPathContextSource ldapContextSource() {
        if(!this.enableLdap) return null;
        if(this.ldapContextSource != null) return this.ldapContextSource;
        
        DefaultSpringSecurityContextSource defaultSpringSecurityContextSource
            = new DefaultSpringSecurityContextSource(this.ldapUrl);
        defaultSpringSecurityContextSource.setUserDn(this.ldapUserDN);
        defaultSpringSecurityContextSource.setPassword(this.ldapPassword);
        return defaultSpringSecurityContextSource;
    }

    private LdapUserSearch userSearch() {
        if(this.userSearch != null) return this.userSearch;
        
        return new FilterBasedLdapUserSearch(this.ldapSearchBase, this.ldapSearchFilter, this.ldapContextSource());
    }

    //=======CAS======
    @Value("${cas.enabled}")
    private boolean enableCas;
    @Value("${cas.localSystemUrl}")
    private String localSystemUrl;
    @Value("${cas.url}")
    private String casUrl;
    @Value("${cas.loginUrl}")
    private String casLoginUrl;
    @Value("${cas.logoutUrl}")
    private String casLogoutUrl;
    
    private final static String SPRING_SECURITY_CAS_CHECK_URL="/j_spring_security_cas_check";    
    
    private Filter casAuthenticationFilter;
    private Filter singleLogoutFilter;
    private Filter requestSingleLogoutFilter;
    private ServiceProperties serviceProperties;
    private AuthenticationProvider casAuthenticationProvider;
    private AuthenticationEntryPoint casEntryPoint;
    
    private Filter casAuthenticationFilter(){
        if(this.casAuthenticationFilter != null) return this.casAuthenticationFilter;
        
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(this.casAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviders);
        
        CasAuthenticationFilter casFilter = new CasAuthenticationFilter();
        casFilter.setAuthenticationManager(authenticationManager);
        casFilter.setFilterProcessesUrl(SecurityConfiguration.SPRING_SECURITY_CAS_CHECK_URL);
        return casFilter;
    }
    
    private Filter requestSingleLogoutFilter(){
        if(this.requestSingleLogoutFilter != null) return this.requestSingleLogoutFilter;
        
        LogoutFilter requestSingleLogoutFilter = new LogoutFilter(this.casLogoutUrl, new SecurityContextLogoutHandler());
        requestSingleLogoutFilter.setFilterProcessesUrl(SecurityConfiguration.SPRING_SECURITY_LOGOUT_URL);
        return requestSingleLogoutFilter;
    }
    
    private Filter singleLogoutFilter(){
        if(this.singleLogoutFilter != null) return this.singleLogoutFilter;
        
        return new SingleSignOutFilter();
    }
    
    private ServiceProperties serviceProperties(){
        if(this.serviceProperties != null) return this.serviceProperties;
        
        ServiceProperties serviceProperties = new ServiceProperties();
        while(this.localSystemUrl.endsWith("/")){
            this.localSystemUrl.substring(0, this.localSystemUrl.length()-1);
        }
        serviceProperties.setService(this.localSystemUrl+SecurityConfiguration.SPRING_SECURITY_CAS_CHECK_URL);
        serviceProperties.setSendRenew(false);
        
        return serviceProperties;
    }
    
    private AuthenticationEntryPoint casEntryPoint(){
        if(this.casEntryPoint != null) return this.casEntryPoint;
        
        CasAuthenticationEntryPoint point = new CasAuthenticationEntryPoint();
        point.setLoginUrl(this.casLoginUrl);
        point.setServiceProperties(this.serviceProperties());
        return point;
    }
    
    private AuthenticationProvider casAuthenticationProvider(){
        if(this.casAuthenticationProvider != null) return this.casAuthenticationProvider;
        
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);
        provider.setServiceProperties(this.serviceProperties());
        provider.setTicketValidator(new Cas20ServiceTicketValidator(this.casUrl));
        provider.setKey("an_id_for_this_auth_provider_only");
        return provider;
    }
}
