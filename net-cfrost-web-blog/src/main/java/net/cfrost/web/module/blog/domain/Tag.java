package net.cfrost.web.module.blog.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.cfrost.web.base.domain.BaseEntity;


@SuppressWarnings("serial")
@Entity
@Table(name="BLG_TAG")
public class Tag extends BaseEntity<Tag> {
    
}
