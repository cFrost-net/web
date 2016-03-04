package net.cfrost.web.module.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.cfrost.web.core.domain.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="blog")
public class Blog extends BaseEntity<Blog> {  
    
    @Id
    @Column(name="blog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
