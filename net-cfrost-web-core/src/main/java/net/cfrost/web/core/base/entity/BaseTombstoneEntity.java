package net.cfrost.web.core.base.entity;

import java.util.Date;

/**
 * 
 * @author cFrost
 * @see com.sfa.web.core.base.entity.NonIDTombstoneEntity
 * @see com.sfa.web.core.base.entity.IDTombstoneEntity
 * @see com.sfa.web.core.base.entity.UUIDTombstoneEntity
 * @param <T>
 */
public interface BaseTombstoneEntity<T extends BaseTombstoneEntity<?>> extends BaseEntity<T> {    

    /**
     * 获取记录创建者<br>
     * 一般为创建用户的ID或UUID
     * @return
     */
    public String getCreateBy();

    /**
     * 设置记录创建者<br>
     * 一般传入创建用户的ID或UUID
     * @param createBy
     */
    public void setCreateBy(String createBy);

    /**
     * 获取记录修改者<br>
     * 一般为创建用户的ID或UUID
     * @return
     */
    public String getModifyBy();

    /**
     * 设置记录修改者<br>
     * 一般传入创建用户的ID或UUID
     * @param modifyBy
     */
    public void setModifyBy(String modifyBy);

    /**
     * 创建时间<br>
     * @return
     */
    public Date getCreateDate();

    /**
     * 创建时间<br>
     * @param createDate
     */
    public void setCreateDate(Date createDate);

    /**
     * 修改时间<br>
     * @return
     */
    public Date getModifyDate();

    /**
     * 修改时间<br>
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) ;

    /**
     * 获取删除标记
     * @return
     */
    public boolean getIfDel();

    /**
     * 设置删除标记
     * @param ifDel
     */
    public void setIfDel(boolean ifDel);
}
