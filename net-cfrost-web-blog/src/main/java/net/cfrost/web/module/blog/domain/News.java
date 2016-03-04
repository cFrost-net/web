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
@Table(name="news")
public class News extends BaseEntity<News> {   
    
    @Id
    @Column(name="news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="test")
    private Long createBy;


    public Long getCreateBy() {
        return createBy;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}
