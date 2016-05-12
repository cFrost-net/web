package net.cfrost.web.core.base.entity;

import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class NonIDEntity<T extends NonIDEntity<?>> implements BaseEntity<T> {
    
}
