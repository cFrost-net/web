package net.cfrost.web.module.blog.rest;

import javax.annotation.Resource;

import net.cfrost.web.core.base.entity.RetResult;
import net.cfrost.web.core.base.rest.BaseRest;
import net.cfrost.web.module.blog.entity.Blog;
import net.cfrost.web.module.blog.service.IBlogService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping("/blog")
public class BlogRest extends BaseRest {
    
    @Resource
    private IBlogService blogService;

    @ResponseBody
    @RequestMapping(value = "/loadBlog/all", method = RequestMethod.GET)
    public RetResult<Blog> findAllBlogs() {
        RetResult<Blog> ret = new RetResult<Blog>();
        ret.setData(this.blogService.findAllBlogs());
        ret.setReturnFlag(RetResult.SUCCESS);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/loadBlog/{id}", method = RequestMethod.GET)
    public RetResult<Blog> findBlog(@PathVariable("id") long id) {
        RetResult<Blog> ret = new RetResult<Blog>();
        ret.setData(this.blogService.find(id));
        ret.setReturnFlag(RetResult.SUCCESS);
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
