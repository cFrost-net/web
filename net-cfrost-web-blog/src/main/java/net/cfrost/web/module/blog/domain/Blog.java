package net.cfrost.web.module.blog.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.cfrost.web.core.base.domain.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="BLG_BLOG")
public class Blog extends BaseEntity<Blog> {
    
    private String name;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
