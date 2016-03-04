package net.cfrost.web.module.blog.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import net.cfrost.web.module.blog.dao.IBlogDao;
import net.cfrost.web.module.blog.domain.Blog;
import net.cfrost.web.module.blog.service.IBlogService;

public class BlogService implements IBlogService {

    private IBlogDao blogDao;
    @Override
    @Transactional
    public void ShowText() {
        Blog blog = new Blog();
        blog.setCreateDate(new Date());
        blog.setIfDel(0);
        blog.setName("中文新2");
        this.blogDao.saveOrUpdate(blog);
        blog = this.blogDao.get(blog.getId());
        System.out.println("new:"+blog.getId()+":"+blog.getName());
        blog = this.blogDao.get(1L);
        System.out.println("old:"+blog.getId()+":"+blog.getName());
    }
    
    public void setBlogDao(IBlogDao blogDao) {
        this.blogDao = blogDao;
    }
}
