package net.cfrost.web.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class IDTombstoneEntity<T extends IDTombstoneEntity<?>> extends IDEntity<T> implements BaseTombstoneEntity<T> {    

    @Column(name="CREATE_BY")
    private Long createBy;
    @Column(name="MODIFY_BY")
    private Long modifyBy;
    @Column(name="CREATE_DATE")
    private Date createDate;
    @Column(name="MODIFY_DATE")
    private Date modifyDate;
    @Column(name="IF_DEL")
    private boolean ifDel;

    @Override
    @XmlElement
    public Long getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(Serializable createBy) {
        this.createBy = (Long) createBy;
    }

    @Override
    @XmlElement
    public Long getModifyBy() {
        return modifyBy;
    }

    @Override
    public void setModifyBy(Serializable modifyBy) {
        this.modifyBy = (Long) modifyBy;
    }

    @Override
    @XmlElement
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    @XmlElement
    public Date getModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    @XmlTransient
    public boolean getIfDel() {
        return ifDel;
    }

    @Override
    public void setIfDel(boolean ifDel) {
        this.ifDel = ifDel;
    }
}
