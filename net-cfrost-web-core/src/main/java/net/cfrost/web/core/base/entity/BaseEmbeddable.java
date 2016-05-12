package net.cfrost.web.core.base.entity;

import java.io.Serializable;

public interface BaseEmbeddable<T extends BaseEmbeddable<?>> extends Serializable, Comparable<T> {

}
