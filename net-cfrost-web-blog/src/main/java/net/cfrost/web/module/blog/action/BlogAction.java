package net.cfrost.web.module.blog.action;

import net.cfrost.web.core.action.BaseAction;
import net.cfrost.web.module.blog.service.IBlogService;

public class BlogAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private IBlogService blogService;

    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    public String test() throws Exception {
        this.blogService.ShowText();
        return SUCCESS;
    }
}
