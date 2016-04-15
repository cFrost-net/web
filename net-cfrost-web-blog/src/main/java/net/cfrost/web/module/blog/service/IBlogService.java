package net.cfrost.web.module.blog.service;

import java.util.List;

import net.cfrost.web.module.blog.entity.Blog;
import net.cfrost.web.module.blog.entity.Tag;

public interface IBlogService {
    public List<Blog> findAllBlogs();
    public Blog newBlog();
    public Tag newTag();
    public Blog find(long id);
}
