package net.cfrost.web.module.blog.service.impl;

import net.cfrost.web.module.blog.dao.IBlogDao;
import net.cfrost.web.module.blog.dao.ITagDao;
import net.cfrost.web.module.blog.domain.Blog;
import net.cfrost.web.module.blog.domain.Tag;
import net.cfrost.web.module.blog.service.IBlogService;

import org.springframework.transaction.annotation.Transactional;

public class BlogService implements IBlogService {

    private IBlogDao blogDao;
    private ITagDao tagDao;
    
    public void setBlogDao(IBlogDao blogDao) {
        this.blogDao = blogDao;
    }
    
    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }
    @Override
    @Transactional
    public Blog newBlog() {
        Blog blog = new Blog();
        blog.setName("中文新2");
        this.blogDao.saveOrUpdate(blog);
        blog = this.blogDao.get(blog.getId());
        System.out.println("new:"+blog.getId()+":"+blog.getName());
        blog = this.blogDao.get(1L);
        System.out.println("old:"+blog.getId()+":"+blog.getName());
        
        return blog;
    }

    @Override
    @Transactional
    public Tag newTag() {
        Tag tag = new Tag();
        this.tagDao.saveOrUpdate(tag);
        
        return tag;
    }
}
