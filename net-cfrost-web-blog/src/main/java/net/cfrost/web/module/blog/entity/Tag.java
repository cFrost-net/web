package net.cfrost.web.module.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.cfrost.web.core.base.entity.IDTombstoneEntity;


@SuppressWarnings("serial")
@Entity
@Table(name="BLG_TAG")
public class Tag extends IDTombstoneEntity<Tag> {
    
}
