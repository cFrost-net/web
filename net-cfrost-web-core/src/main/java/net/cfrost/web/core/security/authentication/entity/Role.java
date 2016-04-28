package net.cfrost.web.core.security.authentication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.cfrost.web.core.base.entity.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="TS_ROLE")
public class Role extends BaseEntity<Role> {
   
    public final static String ANONYMOUS = "ANONYMOUS";

    @Column(name="NAME",nullable=false,unique=true)
    private String name;
    @Column(name="DESCRIPTION")
    private String description;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }    
    
    
}
