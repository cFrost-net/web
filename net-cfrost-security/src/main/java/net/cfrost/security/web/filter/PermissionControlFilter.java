package net.cfrost.security.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.cfrost.util.web.UrlTool;

public class PermissionControlFilter implements Filter {

    private String[] webFolders;
    
    @Override
    public void init(FilterConfig paramFilterConfig) throws ServletException {
        String paramwWebFolders = paramFilterConfig.getInitParameter("webFolders");
        this.webFolders = paramwWebFolders.replaceAll(" ", "").split(",");
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest paramServletRequest,
            ServletResponse paramServletResponse, FilterChain paramFilterChain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        
        //模块名称 /xxx/yyy/zzz中的xxx
        String moduleName = UrlTool.findModuleName((HttpServletRequest)paramServletRequest, webFolders);

        System.out.println("PermissionControl:Module Name is "+moduleName);
        paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
}
