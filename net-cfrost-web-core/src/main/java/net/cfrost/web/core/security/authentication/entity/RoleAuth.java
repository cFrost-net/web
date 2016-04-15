package net.cfrost.web.core.security.authentication.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.cfrost.web.core.base.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="TS_ROLE_AUTH")
public class RoleAuth extends BaseEntity<RoleAuth> {

    @Column(name="URL_MATCHER",nullable=false,unique=true)
    private String urlMatcher;
    @Column(name="ROLE_IDS")
    private String roleIds;
    @Column(name="ROLE_ORDER")
    private Integer order;
    @Transient
    private Set<String> roles;
    
    public String getUrlMatcher() {
        return urlMatcher;
    }
    public void setUrlMatcher(String urlMatcher) {
        this.urlMatcher = urlMatcher;
    }
    public String getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
}
