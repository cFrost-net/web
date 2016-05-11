package net.cfrost.web.core.base.entity;

import java.io.Serializable;

public interface BaseEntity<T extends BaseEntity<?>> extends Serializable, Comparable<T> {
    
    public Serializable getId();
}
