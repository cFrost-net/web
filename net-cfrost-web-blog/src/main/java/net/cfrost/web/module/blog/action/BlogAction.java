package net.cfrost.web.module.blog.action;

import net.cfrost.web.base.action.BaseAction;
import net.cfrost.web.module.blog.domain.Blog;
import net.cfrost.web.module.blog.domain.Tag;
import net.cfrost.web.module.blog.service.IBlogService;

public class BlogAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private IBlogService blogService;
    
    private Blog blog;
    private Tag tag;
    

    public Blog getBlog() {
        return blog;
    }

    public Tag getTag() {
        return tag;
    }

    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    public String newBlog() throws Exception {
        blog = this.blogService.newBlog();
        return SUCCESS;
    }

    public String newTag() throws Exception {
        tag = this.blogService.newTag();
        return SUCCESS;
    }
}
