package net.cfrost.web.core.base.entity;

import java.io.Serializable;

/**
 * 复合主键组件接口<br>
 * 
 * @param <T> 实现类类名<br>例如:<code>public class Example implements BaseEmbeddable&lt;Example&gt;<code>
 * @author cFrost
 */
public interface BaseEmbeddable<T extends BaseEmbeddable<?>> extends Serializable, Comparable<T> {

}
