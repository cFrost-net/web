package net.cfrost.web.core.security.authentication.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cfrost.web.core.base.entity.BaseEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name="TS_USER")
public class User extends BaseEntity<User> implements UserDetails {

    @Column(name="USERNAME",nullable=false,unique=true)
    private String username;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="ROLE_IDS")
    private String roleIds;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="TS_USER_ROLE",
        joinColumns={@JoinColumn(name="USER_ID")},
        inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
    private Set<Role> roles;
    @Column(name="ENABLED")
    private boolean enabled;
    @Column(name="ACCOUNT_NON_EXPIRED")
    @Transient
    private boolean accountNonExpired;
    @Column(name="CREDENTIALS_NON_EXPIRED")
    @Transient
    private boolean credentialsNonExpired;
    @Column(name="ACCOUNT_NON_LOCKED")
    @Transient
    private boolean accountNonLocked;
    @Transient
    private Set<GrantedAuthority>  authorities;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @XmlTransient
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    public User() {
        super();
    }
    
    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super();
        if ((username == null) || ("".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }
    
    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");

            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }
    
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable{

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}
