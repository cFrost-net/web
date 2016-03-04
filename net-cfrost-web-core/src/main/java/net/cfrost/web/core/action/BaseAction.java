package net.cfrost.web.core.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public abstract class BaseAction extends ActionSupport {
    
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }
    
    public ActionContext getActionContext() {
        return ActionContext.getContext();
    }
    
}
