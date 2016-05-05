package net.cfrost.web.core.security.authentication.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.cfrost.web.core.base.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="TS_AUTH")
public class RoleAuth extends BaseEntity<RoleAuth> {

    @Column(name="URL_MATCHER",nullable=false,unique=true)
    private String urlMatcher;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="TS_AUTH_ROLE",
        joinColumns={@JoinColumn(name="AUTH_ID")},
        inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
    private Set<Role> roles;
    @Column(name="AUTH_ORDER")
    private Integer order;
    
    public String getUrlMatcher() {
        return urlMatcher;
    }
    public void setUrlMatcher(String urlMatcher) {
        this.urlMatcher = urlMatcher;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
}
