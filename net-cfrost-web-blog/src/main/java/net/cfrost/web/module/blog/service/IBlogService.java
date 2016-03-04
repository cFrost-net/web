package net.cfrost.web.module.blog.service;

import net.cfrost.web.module.blog.domain.Blog;
import net.cfrost.web.module.blog.domain.Tag;

public interface IBlogService {
    public Blog newBlog();
    public Tag newTag();
}
