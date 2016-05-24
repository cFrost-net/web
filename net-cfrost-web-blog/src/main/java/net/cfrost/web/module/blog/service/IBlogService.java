package net.cfrost.web.module.blog.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import net.cfrost.web.module.blog.entity.Blog;
import net.cfrost.web.module.blog.entity.Tag;

@PreAuthorize("isFullyAuthenticated() and hasAnyAuthority('VIEW_BLOG', 'ADMIN')")
public interface IBlogService {
    public List<Blog> findAllBlogs();
    public Blog createBlog();
    public Tag createTag();
    public Blog find(long id);
    public List<Blog> findBlogByName(String name);
}
