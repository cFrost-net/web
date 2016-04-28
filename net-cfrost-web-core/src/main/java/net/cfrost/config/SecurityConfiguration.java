package net.cfrost.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.cfrost.web.core.security.authentication.entity.Role;
import net.cfrost.web.core.security.authentication.entity.RoleAuth;
import net.cfrost.web.core.security.authentication.service.IAuthorityService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected final Logger log = LogManager.getLogger();
    
    @Resource
    private IAuthorityService authorityService;
    @Resource
    private UserDetailsService userDetailsService;
    
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
        security.formLogin()
            .loginPage("/login").failureUrl("/login?error")
            .defaultSuccessUrl("/")
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
        .and().logout()
            .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
            .invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .permitAll()
        .and().sessionManagement()
            .sessionFixation().migrateSession()
            .maximumSessions(1).maxSessionsPreventsLogin(true)
        .and().and().csrf().disable();
    }
    
    
    

    @Value("${ldap.enable}")
    private boolean ldapEnable;
    
    @Resource(name="usernamePasswordAuthenticationProvider")
    private AuthenticationProvider usernamePasswordAuthenticationProvider;
    @Resource(name="ldapAuthenticationProvider")
    private AuthenticationProvider ldapAuthenticationProvider;
    @Resource
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(this.dataSource)
//        .usersByUsernameQuery("select USERNAME, PASSWORD, ENABLED from TS_USER where USERNAME = ?")
//        .authoritiesByUsernameQuery("select URR.USERNAME, R.NAME "
//                + "from TS_ROLE R, "
//                + "(select U.USERNAME, UR.ROLE_ID from TS_USER_ROLE UR, "
//                + "(select ID, USERNAME from  TS_USER where USERNAME = ?) U "
//                + "where UR.USER_ID = U.ID) URR where R.ID = URR.ROLE_ID")
//        .passwordEncoder(new BCryptPasswordEncoder());
//        if(this.ldapEnable){
//            auth.authenticationProvider(this.ldapAuthenticationProvider);
//            this.log.info("SPRING SECURITY CONFIG: LDAP ENABLED");
//        }
//        else {
            auth.authenticationProvider(usernamePasswordAuthenticationProvider);
            this.log.info("SPRING SECURITY CONFIG: LDAP DISABLED");
//        }
    }
}
