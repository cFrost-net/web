package net.cfrost.web.module.blog.controller.rest;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.cfrost.web.module.blog.entity.Blog;
import net.cfrost.web.module.blog.service.IBlogService;
import net.eulerform.web.core.base.controller.rest.BaseRest;
import net.eulerform.web.core.base.entity.RestResponseEntity;
import net.eulerform.web.core.base.entity.RestResponseStatus;

@Controller
@Scope("prototype")
@RequestMapping("/blog")
public class BlogRest extends BaseRest {
    
    @Resource
    private IBlogService blogService;

    @ResponseBody
    @RequestMapping(value = "/loadBlog/all", method = RequestMethod.GET)
    public RestResponseEntity<Blog> findAllBlogs() {
    	RestResponseEntity<Blog> ret = new RestResponseEntity<Blog>();
        ret.setData(this.blogService.findAllBlogs());
        ret.setStatus(RestResponseStatus.OK);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/loadBlog/{id}", method = RequestMethod.GET)
    public RestResponseEntity<Blog> findBlog(@PathVariable("id") long id) {
    	RestResponseEntity<Blog> ret = new RestResponseEntity<Blog>();
        ret.setData(this.blogService.find(id));
        ret.setStatus(RestResponseStatus.OK);
        return ret;
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
