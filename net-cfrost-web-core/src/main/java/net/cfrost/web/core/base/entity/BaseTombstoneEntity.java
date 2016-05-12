package net.cfrost.web.core.base.entity;

import java.util.Date;

/**
 * 具有逻辑删除的功能的基础实体接口<br>
 * 可记录数据的创建人,创建时间,修改人,修改时间
 * 实现时应同时实现{@link net.cfrost.web.core.base.entity.BaseEntity}或扩展它的某种实现
 * 
 * @param <T> 实现类类名&nbsp;例如:<br>
 * <code>public class Example implements BaseEntity&lt;Example&gt;, BaseTombstoneEntity&lt;Example&gt;<code>
 * <br><strong>OR</strong><br>
 * <code>public class Example extends IDEntity&lt;Example&gt; implements BaseTombstoneEntity&lt;Example&gt;<code>
 * @author cFrost
 * @see net.cfrost.web.core.base.entity.NonIDTombstoneEntity
 * @see net.cfrost.web.core.base.entity.IDTombstoneEntity
 * @see net.cfrost.web.core.base.entity.UUIDTombstoneEntity
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
