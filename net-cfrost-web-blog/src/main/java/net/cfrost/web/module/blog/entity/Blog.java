package net.cfrost.web.module.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import net.cfrost.web.core.base.entity.BaseInfoEntity;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name="BLG_BLOG")
public class Blog extends BaseInfoEntity<Blog> {
    
    private String name;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
