package net.cfrost.web.module.blog.controller;

import javax.annotation.Resource;

import net.cfrost.web.module.blog.entity.Blog;
import net.cfrost.web.module.blog.service.IBlogService;
import net.eulerform.web.core.annotation.RestEndpoint;
import net.eulerform.web.core.base.controller.rest.BaseRest;
import net.eulerform.web.core.base.entity.WebServiceResponse;
import net.eulerform.web.core.base.entity.WebServiceResponseStatus;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RestEndpoint
@Scope("prototype")
@RequestMapping("/blog")
public class BlogRestEndpoint extends BaseRest {
    
    @Resource
    private IBlogService blogService;

    @ResponseBody
    @RequestMapping(value = "/loadBlog/all", method = RequestMethod.GET)
    public WebServiceResponse<Blog> findAllBlogs() {
    	WebServiceResponse<Blog> wsResponse = new WebServiceResponse<Blog>();
    	wsResponse.setData(this.blogService.findAllBlogs());
    	wsResponse.setStatus(WebServiceResponseStatus.OK);
        return wsResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/loadBlog/{id}", method = RequestMethod.GET)
    public WebServiceResponse<Blog> findBlog(@PathVariable("id") long id) {
    	WebServiceResponse<Blog> wsResponse = new WebServiceResponse<Blog>();
    	wsResponse.setData(this.blogService.find(id));
    	wsResponse.setStatus(WebServiceResponseStatus.OK);
        return wsResponse;
    }

    @RequestMapping(value = "/loadBlogv/all", method = RequestMethod.GET)
    public ModelAndView findAllBlogsv() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(this.blogService.findAllBlogs());
        //mav.setViewName("restView");
        return mav;
    }
    
    @RequestMapping(value = "/loadBlogv/{id}", method = RequestMethod.GET)
    public ModelAndView findBlogv(@PathVariable("id") long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject(this.blogService.find(id));
        //mav.setViewName("restView");
        return mav;
    }
    
    @RequestMapping(value={"/createBlog"},method=RequestMethod.POST)
    @ResponseBody
    public Blog newBlog() {
        return this.blogService.createBlog();
    }
}
