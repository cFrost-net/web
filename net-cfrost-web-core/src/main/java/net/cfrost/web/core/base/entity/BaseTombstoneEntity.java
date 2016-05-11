package net.cfrost.web.core.base.entity;

import java.io.Serializable;
import java.util.Date;

public interface BaseTombstoneEntity<T extends BaseTombstoneEntity<?>> extends BaseEntity<T> {    

    public Serializable getCreateBy();

    public void setCreateBy(Serializable createBy);

    public Serializable getModifyBy();

    public void setModifyBy(Serializable modifyBy);

    public Date getCreateDate();

    public void setCreateDate(Date createDate);

    public Date getModifyDate();

    public void setModifyDate(Date modifyDate) ;

    public boolean getIfDel();

    public void setIfDel(boolean ifDel);
}
