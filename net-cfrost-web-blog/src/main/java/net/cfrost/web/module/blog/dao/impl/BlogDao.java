package net.cfrost.web.module.blog.dao.impl;

import net.cfrost.web.core.dao.hibernate5.impl.BaseDao;
import net.cfrost.web.module.blog.dao.IBlogDao;
import net.cfrost.web.module.blog.domain.Blog;
import net.cfrost.web.module.blog.domain.News;

public class BlogDao extends BaseDao<Blog> implements IBlogDao {

    public static void main(String[] args){
        BlogDao b = new BlogDao();
        System.out.println(b.entityClass);
        News news1 = new News();
        News news2 = new News();
        news1.setId(6666L);
        news2.setId(6666L);
        System.out.println(news1.compareTo(news2));
        System.out.println(news1.hashCode());
    }
}
