package net.cfrost.web.module.blog.service.impl;

import java.util.List;

import net.eulerform.web.core.base.service.impl.BaseService;
import net.cfrost.web.module.blog.dao.IBlogDao;
import net.cfrost.web.module.blog.dao.ITagDao;
import net.cfrost.web.module.blog.entity.Blog;
import net.cfrost.web.module.blog.entity.Tag;
import net.cfrost.web.module.blog.service.IBlogService;

import org.springframework.transaction.annotation.Transactional;

public class BlogService extends BaseService implements IBlogService {

    private IBlogDao blogDao;
    private ITagDao tagDao;
    
    public void setBlogDao(IBlogDao blogDao) {
        this.blogDao = blogDao;
    }
    
    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }
    @Override
    public Blog createBlog() {
        Blog blog = new Blog();
        blog.setName("新ABC");
        this.blogDao.saveOrUpdate(blog);        
        return blog;
    }

    @Override
    public Tag createTag() {
        Tag tag = new Tag();
        this.tagDao.saveOrUpdate(tag);
        
        return tag;
    }

    @Override
    public List<Blog> findAllBlogs() {
        this.log.warn("test warn");
        this.log.info("test info");
        this.log.error("test error");
        return this.blogDao.findAll();
    }

    @Override
    @Transactional
    public Blog find(long id) {
        return this.blogDao.load(id);
    }
}
