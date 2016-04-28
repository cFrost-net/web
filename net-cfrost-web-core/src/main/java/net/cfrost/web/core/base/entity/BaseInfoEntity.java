package net.cfrost.web.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseInfoEntity<T extends BaseInfoEntity<?>> extends BaseEntity<T> implements Serializable, Comparable<T> {    

    @Column(name="CREATE_BY")
    private Long createBy;
    @Column(name="MODIFY_BY")
    private Long modifyBy;
    @Column(name="CREATE_DATE")
    private Date createDate;
    @Column(name="MODIFY_DATE")
    private Date modifyDate;
    @Column(name="IF_DEL")
    private Integer ifDel;

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
}
