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
@Table(name="tag")
public class Tag extends BaseEntity<Tag> {
    
    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
