package net.cfrost.web.core.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class UUIDTombstoneEntity<T extends UUIDTombstoneEntity<?>> extends UUIDEntity<T> implements BaseTombstoneEntity<T> {    

    @Column(name="CREATE_BY")
    private String createBy;
    @Column(name="MODIFY_BY")
    private String modifyBy;
    @Column(name="CREATE_DATE")
    private Date createDate;
    @Column(name="MODIFY_DATE")
    private Date modifyDate;
    @Column(name="IF_DEL")
    private boolean ifDel;

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String getModifyBy() {
        return modifyBy;
    }

    @Override
    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Date getModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean getIfDel() {
        return ifDel;
    }

    @Override
    public void setIfDel(boolean ifDel) {
        this.ifDel = ifDel;
    }
}
