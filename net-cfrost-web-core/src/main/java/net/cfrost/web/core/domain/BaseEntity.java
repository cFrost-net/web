package net.cfrost.web.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.cfrost.web.core.exception.EntityCompareException;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<?>> implements Serializable, Comparable<T> {
    
    //@Transient
    private Long id;
    @Transient
    private Long createBy;
    private Long modifyBy;
    private Date createDate;
    private Date modifyDate;
    private Integer ifDel;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getIfDel() {
        return ifDel;
    }

    public void setIfDel(Integer ifDel) {
        this.ifDel = ifDel;
    }

    @Override 
    public int compareTo(T obj) {
        if (this == obj)
            return 0;
        
        if(obj == null)
            return 1;
        
        if (getClass() != obj.getClass())
            throw new EntityCompareException("两比较对象类型不一致");
        
        if(this.getId() == null)
            return 1;
        
        if(obj.getId() == null)
            return -1;
        
        return this.getId().compareTo(obj.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity<?> other = (BaseEntity<?>) obj;
        if (this.getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }
}
